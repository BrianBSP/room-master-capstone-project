package brianpelinku.room_master_capstone_project.utenti;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UtentiDTO(
        @NotEmpty(message = "Campo obbligatorio. Inserire nome.")
        @Size(min = 3, message = "Il nome inserito è troppo corto. Almeno 3 caratteri.")
        @Size(max = 30, message = "Il nome inserito è troppo lungo. Massimo 30 caratteri.")
        String nome,
        @NotEmpty(message = "Campo obbligatorio. Inserire cognome.")
        @Size(min = 3, message = "Il cognome inserito è troppo corto. Almeno 3 caratteri.")
        @Size(max = 30, message = "Il cognome inserito è troppo lungo. Massimo 30 caratteri.")
        String cognome,
        @NotEmpty(message = "Campo obbligatorio. Inserire email.")
        @Email(message = "Inserire una email corretta.")
        String email,
        @NotEmpty(message = "Campo obbligatorio. Inserire password.")
        @Size(min = 3, max = 30, message = "La password deve essere compreso tra 3 e 30 caratteri")
        String password
) {
}
