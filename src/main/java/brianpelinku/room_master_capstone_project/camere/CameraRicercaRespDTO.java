package brianpelinku.room_master_capstone_project.camere;

import brianpelinku.room_master_capstone_project.enums.StatoCamera;
import brianpelinku.room_master_capstone_project.enums.TipoCamera;
import brianpelinku.room_master_capstone_project.hotels.Hotel;

import java.util.UUID;

public record CameraRicercaRespDTO(UUID cameraId, int numeroCamera, int capienzaCamera, TipoCamera tipoCamera,
                                   StatoCamera statoCamera, Hotel hotel) {
}
