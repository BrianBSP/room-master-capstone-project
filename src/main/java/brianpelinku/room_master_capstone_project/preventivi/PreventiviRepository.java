package brianpelinku.room_master_capstone_project.preventivi;

import brianpelinku.room_master_capstone_project.utenti.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PreventiviRepository extends JpaRepository<Preventivo, UUID> {

    List<Preventivo> findByUtente(Utente utente);

    List<Preventivo> findByAccettato(boolean accettato);

    @Query("SELECT p FROM Preventivo p WHERE EXTRACT(YEAR FROM p.data) = :anno")
    List<Preventivo> findByAnno(@Param("anno") int anno);

    Optional<Preventivo> findByIdAndUtente(UUID id, Utente utente);

    /*Optional<Preventivo> findByIdAndUtenteAndAccetta(UUID )*/
}
