package brianpelinku.room_master_capstone_project.preventivi;

import brianpelinku.room_master_capstone_project.exceptions.Validation;
import brianpelinku.room_master_capstone_project.prenotazioni.Prenotazione;
import brianpelinku.room_master_capstone_project.utenti.Utente;
import brianpelinku.room_master_capstone_project.utenti.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/preventivi")
public class PreventiviController {

    @Autowired
    private PreventiviService preventiviService;

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private Validation validation;

    @Autowired
    private PagedResourcesAssembler<Preventivo> pagedResourcesAssembler;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public PagedModel<EntityModel<PreventivoRicercaRespDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "5") int size,
                                                                    @RequestParam(defaultValue = "id") String sortBy) {
        Page<Preventivo> preventivoPage = this.preventiviService.findAll(page, size, sortBy);

        /*return (PagedModel<Preventivo>) this.preventiviService.findAll(page, size, sortBy);*/
        return pagedResourcesAssembler.toModel(preventivoPage, preventivo -> {

            PreventivoRicercaRespDTO dto = new PreventivoRicercaRespDTO(preventivo.getId(),
                    preventivo.getData(), preventivo.getArrivo(), preventivo.getPartenza(),
                    preventivo.getTipoCamera(), preventivo.getTipoServizio(),
                    preventivo.getPeriodoSoggiorno(), preventivo.getNumeroAdulti(),
                    preventivo.getNumeroBambini(), preventivo.getTotalePrezzoPreventivo(),
                    preventivo.isAccettato(), preventivo.getUtente());

            return EntityModel.of(dto);
        });
    }

    @GetMapping("/accettati")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PagedModel<EntityModel<PreventivoRicercaRespDTO>> getAllAccettatoTrue(@RequestParam(defaultValue = "0") int page,
                                                                                 @RequestParam(defaultValue = "5") int size,
                                                                                 @RequestParam(defaultValue = "id") String sortBy) {
        Page<Preventivo> preventivoPage = this.preventiviService.findByAccettatoTrue(page, size, sortBy);
        return pagedResourcesAssembler.toModel(preventivoPage, preventivo -> {
            PreventivoRicercaRespDTO dto = new PreventivoRicercaRespDTO(preventivo.getId(),
                    preventivo.getData(), preventivo.getArrivo(), preventivo.getPartenza(),
                    preventivo.getTipoCamera(), preventivo.getTipoServizio(),
                    preventivo.getPeriodoSoggiorno(), preventivo.getNumeroAdulti(),
                    preventivo.getNumeroBambini(), preventivo.getTotalePrezzoPreventivo(),
                    preventivo.isAccettato(), preventivo.getUtente());

            return EntityModel.of(dto);
        });
    }

    @GetMapping("/anno/{anno}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PagedModel<EntityModel<PreventivoRicercaRespDTO>> findByAnno(@PathVariable int anno,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "5") int size,
                                                                        @RequestParam(defaultValue = "id") String sortBy) {
        Page<Preventivo> preventivoPage = this.preventiviService.findByAnno(page, size, sortBy, anno);

        return pagedResourcesAssembler.toModel(preventivoPage, preventivo -> {
            PreventivoRicercaRespDTO dto = new PreventivoRicercaRespDTO(preventivo.getId(),
                    preventivo.getData(), preventivo.getArrivo(), preventivo.getPartenza(),
                    preventivo.getTipoCamera(), preventivo.getTipoServizio(),
                    preventivo.getPeriodoSoggiorno(), preventivo.getNumeroAdulti(),
                    preventivo.getNumeroBambini(), preventivo.getTotalePrezzoPreventivo(),
                    preventivo.isAccettato(), preventivo.getUtente());

            return EntityModel.of(dto);
        });
    }


    @GetMapping("/utenti/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Preventivo> findByUtente(@PathVariable UUID utenteId) {
        Utente trovato = this.utentiService.findById(utenteId);
        return this.preventiviService.findByUtente(trovato);
    }

    @GetMapping("/search/{parola}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Preventivo> findByNomeAndCognomeUtente(@PathVariable("parola") String parola) {
        return this.preventiviService.findByNomeAndCognomeUtente(parola);
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public PreventiviRespDTO save(@RequestBody @Validated PreventiviDTO body, BindingResult validation) {
        this.validation.validate(validation);
        return new PreventiviRespDTO(this.preventiviService.save(body).preventiviId());
    }

    @GetMapping("me")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    public List<Preventivo> findAllByUtente(@AuthenticationPrincipal Utente utente) {
        return this.preventiviService.findAllByUtente(utente);
    }

    @GetMapping("me/{preventivoId}")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    public Preventivo findByIdAndUtente(@PathVariable UUID preventivoId, @AuthenticationPrincipal Utente utente) {
        return this.preventiviService.findByIdAndUtente(preventivoId, utente);
    }

    @GetMapping("/{preventivoId}")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    public Preventivo findById(@PathVariable UUID preventivoId) {
        return this.preventiviService.findById(preventivoId);
    }

    @PutMapping("/{preventivoId}")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    public PreventiviRespDTO findByIdAndUpdate(@PathVariable UUID preventivoId, @RequestBody @Validated PreventiviDTO body) {
        return this.preventiviService.findByIdAndUpdate(preventivoId, body);
    }

    @DeleteMapping("/{preventivoId}")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID preventivoId) {
        this.preventiviService.findByIdAndDelete(preventivoId);
    }

    @PatchMapping("/me/{preventivoId}/accetta")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    public Prenotazione findByIdAndUtenteAndAccettato(@PathVariable UUID preventivoId, @AuthenticationPrincipal Utente utente) {
        return this.preventiviService.accettaPreventivoEAssegnaCamere(preventivoId, utente);
    }


}
