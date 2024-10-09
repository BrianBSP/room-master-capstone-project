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
        @NotEmpty(message = "Inserire il tipo di Camera: STANDARD, FAMILY_ROOM, SUITE")
        String tipoCamera,
        @NotEmpty(message = "Inserire il tipo di Servizio: ALL_INCLUSIVE, PENSIONE_COMPLETA, MEZZA_PENSIONE, BAD_BREAKFAST")
        String tipoServizio
) {
}
