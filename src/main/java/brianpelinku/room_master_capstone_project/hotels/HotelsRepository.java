package brianpelinku.room_master_capstone_project.hotels;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HotelsRepository extends JpaRepository<Hotel, UUID> {
}
