package brianpelinku.room_master_capstone_project.listiniPrezzi;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ListiniDTO(
        @NotEmpty(message = "Inserisci il periodo del soggiorno.")
        String periodoSoggiorno,
        @NotEmpty(message = "Inserisci il tipo di camera.")
        String tipoCamera,
        @NotEmpty(message = "Inserisci il tipo di servizio.")
        String tipoServizio,
        @NotNull(message = "Inserisci il prezzo per un adulto a notte.")
        double prezzoAdultoNotte,
        @NotNull(message = "Inserisci il prezzo per un bambino a notte.")
        double prezzoBambinoNotte,
        @NotEmpty(message = "Inserisci l'id dell'hotel di riferimento.")
        String hotelId
) {
}
