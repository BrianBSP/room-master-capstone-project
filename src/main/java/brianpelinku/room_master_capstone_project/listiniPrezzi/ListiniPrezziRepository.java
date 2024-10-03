package brianpelinku.room_master_capstone_project.listiniPrezzi;

import brianpelinku.room_master_capstone_project.hotels.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ListiniPrezziRepository extends JpaRepository<ListinoPrezzi, UUID> {

    List<ListinoPrezzi> findByHotel(Hotel hotel);
}
