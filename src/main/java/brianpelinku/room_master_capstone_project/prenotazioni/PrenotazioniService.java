package brianpelinku.room_master_capstone_project.prenotazioni;

import brianpelinku.room_master_capstone_project.camere.Camera;
import brianpelinku.room_master_capstone_project.camere.CamereRepository;
import brianpelinku.room_master_capstone_project.enums.StatoCamera;
import brianpelinku.room_master_capstone_project.exceptions.NotFoundException;
import brianpelinku.room_master_capstone_project.utenti.Utente;
import brianpelinku.room_master_capstone_project.utenti.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioniService {

    @Autowired
    private PrenotazioniRepository prenotazioniRepository;

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private CamereRepository camereRepository;

    public Prenotazione creaPrenotazione(Prenotazione prenotazione) {
        return prenotazioniRepository.save(prenotazione);
    }

    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioniRepository.findAll(pageable);
    }

    public List<Prenotazione> findByUtente(Utente utente) {
        Utente trovato = this.utentiService.findById(utente.getId());
        return this.prenotazioniRepository.findByUtente(trovato);
    }

    public List<Prenotazione> findByAnno(int anno) {
        return this.prenotazioniRepository.findByAnno(anno);
    }

    public Prenotazione findById(UUID prenotazioneId) {
        return this.prenotazioniRepository.findById(prenotazioneId)
                .orElseThrow(() -> new NotFoundException("Prenotazione con id " + prenotazioneId + " non trovato."));
    }

    public Prenotazione findByIdAndUtente(UUID prenotazioneId, Utente utente) {
        return this.prenotazioniRepository.findByIdAndUtente(prenotazioneId, utente)
                .orElseThrow(() -> new NotFoundException("Prenotazione non trovata per l'utente: " + utente.getEmail()));
    }

    public void findByIdAndDelete(UUID prenotazioneId) {
        Prenotazione trovato = this.findById(prenotazioneId);

        List<Camera> camereAssegnate = trovato.getCamere();
        for (Camera camera : camereAssegnate) {
            camera.setStatoCamera(StatoCamera.DISPONIBILE);
            camera.setPrenotazione(null);
            camereRepository.save(camera);
        }
            
        this.prenotazioniRepository.delete(trovato);
    }

    public List<Prenotazione> findAllByUtente(Utente utente) {
        return this.prenotazioniRepository.findAllByUtente(utente);
    }

    public Prenotazione findByPreventivoId(UUID preventivoId) {
        return this.prenotazioniRepository.findByPreventivoId(preventivoId)
                .orElseThrow(() -> new NotFoundException("Nessuna prenotazione per il preventivo con Id: " + preventivoId));
    }

    public List<Prenotazione> findByPartenzaBefore(LocalDate partenza) {
        return this.prenotazioniRepository.findByPartenzaBefore(partenza);
    }


}
