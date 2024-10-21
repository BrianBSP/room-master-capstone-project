package brianpelinku.room_master_capstone_project.prenotazioni;

import brianpelinku.room_master_capstone_project.preventivi.Preventivo;
import brianpelinku.room_master_capstone_project.utenti.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrenotazioniRepository extends JpaRepository<Prenotazione, UUID> {

    Prenotazione findByPreventivo(Preventivo preventivo);

    Optional<Prenotazione> findByIdAndUtente(UUID id, Utente utente);

    @Query("SELECT p FROM Prenotazione p WHERE EXTRACT(YEAR FROM p.arrivo) = :anno")
    Page<Prenotazione> findByAnno(@Param("anno") int anno, Pageable pageable);

    List<Prenotazione> findByUtente(Utente utente);

    @Query("SELECT p FROM Prenotazione p WHERE p.utente = :utente")
    List<Prenotazione> findAllByUtente(@Param("utente") Utente utente);

    @Query("SELECT p FROM Prenotazione p WHERE p.preventivo.id = :preventivoId")
    Optional<Prenotazione> findByPreventivoId(UUID preventivoId);

    List<Prenotazione> findByPartenzaBefore(LocalDate partenza);

    @Query("SELECT p FROM Prenotazione p JOIN p.utente u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :parola, '%')) OR LOWER(u.cognome) LIKE LOWER(CONCAT('%', :parola, '%'))")
    List<Prenotazione> findByNomeAndCognomeUtente(@Param("parola") String parola);

}
