package brianpelinku.room_master_capstone_project.preventivi;

import brianpelinku.room_master_capstone_project.utenti.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<Preventivo> findByAccettatoTrue(Pageable pageable);

    @Query("SELECT p FROM Preventivo p WHERE EXTRACT(YEAR FROM p.data) = :anno")
    Page<Preventivo> findByAnno(@Param("anno") int anno, Pageable pageable);

    Optional<Preventivo> findByIdAndUtente(UUID id, Utente utente);

    @Query("SELECT p FROM Preventivo p WHERE p.utente = :utente")
    List<Preventivo> findAllByUtente(@Param("utente") Utente utente);

    @Query("SELECT p FROM Preventivo p JOIN p.utente u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :parola, '%')) OR LOWER(u.cognome) LIKE LOWER(CONCAT('%', :parola, '%'))")
    List<Preventivo> findByNomeAndCognomeUtente(@Param("parola") String parola);
}
