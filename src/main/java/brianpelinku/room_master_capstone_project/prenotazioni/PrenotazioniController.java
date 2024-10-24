package brianpelinku.room_master_capstone_project.prenotazioni;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {

    @Autowired
    private PrenotazioniService prenotazioniService;

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private PagedResourcesAssembler<Prenotazione> pagedResourcesAssembler;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public PagedModel<EntityModel<PrenotazioneRicercaRespDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "5") int size,
                                                                      @RequestParam(defaultValue = "id") String sortBy) {
        Page<Prenotazione> prenotazionePage = this.prenotazioniService.findAll(page, size, sortBy);
        return pagedResourcesAssembler.toModel(prenotazionePage, prenotazione -> {
            PrenotazioneRicercaRespDTO dto = new PrenotazioneRicercaRespDTO(prenotazione.getId(), prenotazione.getArrivo(),
                    prenotazione.getPartenza(), prenotazione.getTotalePrezzo(), prenotazione.getUtente(),
                    prenotazione.getPreventivo(), prenotazione.getCamere());
            return EntityModel.of(dto);
        });
    }

    @GetMapping("/utenti/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Prenotazione> findByUtente(@PathVariable UUID utenteId) {
        Utente trovato = this.utentiService.findById(utenteId);
        return this.prenotazioniService.findByUtente(trovato);
    }

    @GetMapping("/anno/{anno}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PagedModel<EntityModel<PrenotazioneRicercaRespDTO>> findByAnno(@PathVariable int anno,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "5") int size,
                                                                          @RequestParam(defaultValue = "id") String sortBy) {
        Page<Prenotazione> prenotazionePage = this.prenotazioniService.findByAnno(page, size, sortBy, anno);

        return pagedResourcesAssembler.toModel(prenotazionePage, prenotazione -> {
            PrenotazioneRicercaRespDTO dto = new PrenotazioneRicercaRespDTO(prenotazione.getId(), prenotazione.getArrivo(),
                    prenotazione.getPartenza(), prenotazione.getTotalePrezzo(), prenotazione.getUtente(),
                    prenotazione.getPreventivo(), prenotazione.getCamere());
            return EntityModel.of(dto);
        });
    }

    @GetMapping("/search/{parola}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Prenotazione> findByNomeAndCognomeUtente(@PathVariable("parola") String parola) {
        return this.prenotazioniService.findByNomeAndCognomeUtente(parola);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    public List<Prenotazione> findAllByUtente(@AuthenticationPrincipal Utente utente) {
        return this.prenotazioniService.findAllByUtente(utente);
    }

    @GetMapping("me/{prenotazioneId}")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    public Prenotazione findByIdAndUtente(@PathVariable UUID prenotazioneId, @AuthenticationPrincipal Utente utente) {
        return this.prenotazioniService.findByIdAndUtente(prenotazioneId, utente);
    }

    @GetMapping("/{prenotazioneId}")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    public Prenotazione findById(@PathVariable UUID prenotazioneId) {
        return this.prenotazioniService.findById(prenotazioneId);
    }

    /*@PutMapping("/{prenotazioneId}")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    public PrenotazioniRespDTO findByIdAndUpdate(@PathVariable UUID prenotazioneId, @RequestBody @Validated PrenotazioneDTO body) {
        return this.prenotazioniService.findByIdAndUpdate(prenotazioneId, body);
    }*/

    @DeleteMapping("/{prenotazioneId}")
    @PreAuthorize("hasAnyAuthority('UTENTE','ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID prenotazioneId) {
        this.prenotazioniService.findByIdAndDelete(prenotazioneId);
    }

}
