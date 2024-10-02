package brianpelinku.room_master_capstone_project.utenti;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UtentiLoginDTO(
        @NotEmpty(message = "Email obbligatoria.")
        @Email(message = "Email non valida.")
        String email,
        @NotEmpty(message = "Password obbligatoria.")
        String password
) {
}
