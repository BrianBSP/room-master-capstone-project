package brianpelinku.room_master_capstone_project.preventivi;

import brianpelinku.room_master_capstone_project.enums.PeriodoSoggiorno;
import brianpelinku.room_master_capstone_project.enums.TipoCamera;
import brianpelinku.room_master_capstone_project.enums.TipoServizio;
import brianpelinku.room_master_capstone_project.exceptions.BadRequestException;
import brianpelinku.room_master_capstone_project.exceptions.NotFoundException;
import brianpelinku.room_master_capstone_project.utenti.Utente;
import brianpelinku.room_master_capstone_project.utenti.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PreventiviService {

    @Autowired
    private PreventiviRepository preventiviRepository;

    @Autowired
    private UtentiService utentiService;

    public Page<Preventivo> findAll(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.preventiviRepository.findAll(pageable);
    }

    public List<Preventivo> findByUtente(Utente utente) {
        Utente trovato = this.utentiService.findById(utente.getId());
        return this.preventiviRepository.findByUtente(trovato);
    }

    public List<Preventivo> findByAccettato(boolean accettato) {
        return this.preventiviRepository.findByAccettato(accettato);
    }

    public List<Preventivo> findByAnno(int anno) {
        return this.preventiviRepository.findByAnno(anno);
    }

    public Preventivo findById(UUID preventivoId) {
        return this.preventiviRepository.findById(preventivoId).orElseThrow(() -> new NotFoundException("Preventivo con id " + preventivoId + " non trovato."));
    }

    public PreventiviRespDTO save(PreventiviDTO body) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente utente = (Utente) authentication.getPrincipal();

        Preventivo preventivo = new Preventivo();
        preventivo.setData(LocalDate.now());
        if (LocalDate.parse(body.arrivo()).isBefore(LocalDate.now()))
            throw new BadRequestException("Impossibile inserire una data di arrivo precedente ad oggi.");
        preventivo.setArrivo(LocalDate.parse(body.arrivo()));
        if (LocalDate.parse(body.partenza()).isBefore(LocalDate.parse(body.arrivo())) && LocalDate.parse(body.partenza()).isEqual(LocalDate.parse(body.arrivo())))
            throw new BadRequestException("Inserire correttamente la data di partenza.");
        preventivo.setPartenza(LocalDate.parse(body.partenza()));
        preventivo.setNumeroAdulti(body.numeroAdulti());
        preventivo.setNumeroBambini(body.numeroBambini());
        preventivo.setTipoCamera(TipoCamera.valueOf(body.tipoCamera()));
        if (TipoCamera.valueOf(body.tipoCamera()) == TipoCamera.SUITE) {
            preventivo.setTipoServizio(TipoServizio.ALL_INCLUSIVE);
        } else {
            preventivo.setTipoServizio(TipoServizio.valueOf(body.tipoServizio()));
        }
        preventivo.setPeriodoSoggiorno(PeriodoSoggiorno.valueOf(body.periodoSoggiorno()));
        preventivo.setUtente(utente);
        return new PreventiviRespDTO(this.preventiviRepository.save(preventivo).getId());
    }

    public PreventiviRespDTO findByIdAndUpdate(UUID preventivoId, PreventiviDTO body) {
        Preventivo trovato = this.findById(preventivoId);

        LocalDate arrivo = LocalDate.parse(body.arrivo());
        if (arrivo.isBefore(LocalDate.now())) {
            throw new BadRequestException("Impossibile inserire una data di arrivo precedente ad oggi.");
        }
        trovato.setArrivo(arrivo);

        LocalDate partenza = LocalDate.parse(body.partenza());
        if (partenza.isBefore(arrivo)) {
            throw new BadRequestException("Inserire correttamente la data di partenza.");
        }
        trovato.setPartenza(partenza);

        /*if (LocalDate.parse(body.arrivo()).isBefore(LocalDate.now()))
            throw new BadRequestException("Impossibile inserire una data di arrivo precedente ad oggi.");
        trovato.setArrivo(LocalDate.parse(body.arrivo()));
        if (LocalDate.parse(body.partenza()).isBefore(LocalDate.now()))
            throw new BadRequestException("Inserire correttamente la data di partenza.");
        trovato.setPartenza(LocalDate.parse(body.partenza()));*/
        trovato.setNumeroAdulti(body.numeroAdulti());
        trovato.setNumeroBambini(body.numeroBambini());
        trovato.setTipoCamera(TipoCamera.valueOf(body.tipoCamera()));
        if (TipoCamera.valueOf(body.tipoCamera()) == TipoCamera.SUITE) {
            trovato.setTipoServizio(TipoServizio.ALL_INCLUSIVE);
        } else {
            trovato.setTipoServizio(TipoServizio.valueOf(body.tipoServizio()));
        }
        trovato.setPeriodoSoggiorno(PeriodoSoggiorno.valueOf(body.periodoSoggiorno()));
        return new PreventiviRespDTO(this.preventiviRepository.save(trovato).getId());
    }

    public void findByIdAndDelete(UUID preventivoId) {
        Preventivo trovato = this.findById(preventivoId);
        this.preventiviRepository.delete(trovato);
    }

    public PreventiviRespDTO findByIdAndAccetta(UUID preventivoId) {
        Preventivo trovato = this.findById(preventivoId);
        trovato.setAccettato(true);
        return new PreventiviRespDTO(this.preventiviRepository.save(trovato).getId());
    }

}
