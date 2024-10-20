package brianpelinku.room_master_capstone_project.preventivi;

import brianpelinku.room_master_capstone_project.enums.PeriodoSoggiorno;
import brianpelinku.room_master_capstone_project.enums.TipoCamera;
import brianpelinku.room_master_capstone_project.enums.TipoServizio;
import brianpelinku.room_master_capstone_project.utenti.Utente;

import java.time.LocalDate;
import java.util.UUID;

public record PreventivoRicercaRespDTO(UUID preventivoId, LocalDate data, LocalDate arrivo, LocalDate partenza,
                                       TipoCamera tipoCamera, TipoServizio tipoServizio,
                                       PeriodoSoggiorno periodoSoggiorno, int numeroAdulti, int numeroBambini,
                                       double totalePrezzoPreventivo, boolean accettato, Utente utente) {
}
