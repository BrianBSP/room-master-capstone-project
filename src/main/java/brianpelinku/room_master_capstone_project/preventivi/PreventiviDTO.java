package brianpelinku.room_master_capstone_project.preventivi;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PreventiviDTO(
        @NotEmpty(message = "Inserire una data di arrivo nella struttura. YYYY-MM-DD")
        String arrivo,
        @NotEmpty(message = "Inserire una data di partenza dalla struttura. YYYY-MM-DD")
        String partenza,
        @NotNull(message = "Inserire il numero di adulti.")
        int numeroAdulti,
        @NotNull(message = "Inserire il numero di adulti.")
        int numeroBambini,
        @NotEmpty(message = "Inserire il tipo di Camera:\nSTANDARD, \nFAMILY_ROOM, \nSUITE")
        String tipoCamera,
        @NotEmpty(message = "Inserire il tipo di Servizio:\nALL_INCLUSIVE, \nPENSIONE_COMPLETA, \nMEZZA_PENSIONE, \nBAD_BREAKFAST")
        String tipoServizio,
        @NotEmpty(message = "Inserire il tipo di Servizio:\nALTA_STAGIONE, \nMEDIA_STAGIONE, \nBASSA_STAGIONE")
        String periodoSoggiorno,
        double totalePrezzoPreventivo
) {
}
