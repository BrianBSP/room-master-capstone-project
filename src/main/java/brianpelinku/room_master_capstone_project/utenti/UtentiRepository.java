package brianpelinku.room_master_capstone_project.utenti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtentiRepository extends JpaRepository<Utente, UUID> {

    Optional<Utente> findByEmail(String email);
}
