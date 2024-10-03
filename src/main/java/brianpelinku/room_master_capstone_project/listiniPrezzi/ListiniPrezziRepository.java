package brianpelinku.room_master_capstone_project.listiniPrezzi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ListiniPrezziRepository extends JpaRepository<ListinoPrezzi, UUID> {
}
