package brianpelinku.room_master_capstone_project.camere;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CamereDTO(
        @NotNull(message = "Inserisci il numero della camera.")
        int numeroCamera,
        @NotNull(message = "Inserisci capienza massima della camera.")
        int capienzaCamera,
        @NotEmpty(message = "Inserisci il tipo di camera: FAMILY_ROOM, STANDARD, SUITE.")
        String tipoCamera,
        @NotEmpty(message = "Inserisci lo stato della camera: LIBERA, OCCUPATA, IN_PREPARAZIONE, FUORI_SERVIZIO.")
        String statoCamera,
        @NotEmpty(message = "Inserisci l'hotel di appartenenza.")
        String hotel
) {
}
