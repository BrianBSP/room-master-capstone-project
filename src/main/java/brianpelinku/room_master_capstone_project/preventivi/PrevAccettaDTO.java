package brianpelinku.room_master_capstone_project.preventivi;

import jakarta.validation.constraints.AssertTrue;

public record PrevAccettaDTO(
        @AssertTrue(message = "Accetta il preventivo per poter proseguire con la prenotazione.")
        boolean accettato
) {
}
