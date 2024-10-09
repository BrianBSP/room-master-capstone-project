package brianpelinku.room_master_capstone_project.hotels;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record HotelDTO(
        @NotEmpty(message = "Inserire Nome dell'hotel.")
        String nome,
        @NotEmpty(message = "Inserire il livello di servizio della struttura.")
        String livelloStruttura,
        @NotNull(message = "Inserire in numero di piani")
        int numeroPiani,
        @NotNull(message = "Inserire il numero delle camere disponibili nella struttura")
        int numeroCamere
) {
}
