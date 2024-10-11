package brianpelinku.room_master_capstone_project.camere;

import jakarta.validation.constraints.NotEmpty;

public record CamerePrenotateDTO(
        @NotEmpty(message = "Inserisci la prenotazione per poter proseguire con l'assegnazione delle camere.")
        String prenotazione
) {
}
