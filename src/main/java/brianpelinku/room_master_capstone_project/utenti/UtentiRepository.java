package brianpelinku.room_master_capstone_project.utenti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, UUID> {

    Optional<Utente> findByEmail(String email);

    @Query("SELECT u FROM Utente u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :parola, '%')) OR LOWER(u.cognome) LIKE LOWER(CONCAT('%', :parola, '%'))")
    List<Utente> findByNomeAndCognome(@Param("parola") String parola);
}
