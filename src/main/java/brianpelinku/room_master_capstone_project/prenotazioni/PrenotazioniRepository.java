package brianpelinku.room_master_capstone_project.prenotazioni;

import brianpelinku.room_master_capstone_project.preventivi.Preventivo;
import brianpelinku.room_master_capstone_project.utenti.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {

    Prenotazione findByPreventivo(Preventivo preventivo);

    Optional<Prenotazione> findByIdAndUtente(UUID id, Utente utente);

    @Query("SELECT p FROM Preventivo p WHERE EXTRACT(YEAR FROM p.data) = :anno")
    List<Prenotazione> findByAnno(@Param("anno") int anno);

    List<Prenotazione> findByUtente(Utente utente);

}
