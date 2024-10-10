package brianpelinku.room_master_capstone_project.camere;

import brianpelinku.room_master_capstone_project.enums.TipoCamera;
import brianpelinku.room_master_capstone_project.hotels.Hotel;
import brianpelinku.room_master_capstone_project.prenotazioni.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CamereRepository extends JpaRepository<Camera, UUID> {

    List<Camera> findByPrenotazione(Prenotazione prenotazione);

    Camera findByNumeroCameraAndPrenotazione(int numeroCamera, Prenotazione prenotazione);

    Camera findByNumeroCamera(int numeroCamera);

    List<Camera> findByHotel(Hotel hotel);

    @Query("SELECT c FROM Camera c WHERE c.tipoCamera = :tipoCamera AND c.statoCamera = 'DISPONIBILE'")
    List<Camera> findCamereDisponibili(@Param("tipoCamera") TipoCamera tipoCamera);
}
