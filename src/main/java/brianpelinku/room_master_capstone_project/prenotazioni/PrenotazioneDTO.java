package brianpelinku.room_master_capstone_project.prenotazioni;

import jakarta.validation.constraints.NotEmpty;

public record PrenotazioneDTO(
        @NotEmpty(message = "Inserisci una data di arrivo.")
        String arrivo,
        @NotEmpty(message = "Inserisci una data di partenza corretta.")
        String partenza

) {
}
