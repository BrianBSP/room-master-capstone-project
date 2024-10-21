package brianpelinku.room_master_capstone_project.prenotazioni;

import brianpelinku.room_master_capstone_project.camere.Camera;
import brianpelinku.room_master_capstone_project.preventivi.Preventivo;
import brianpelinku.room_master_capstone_project.utenti.Utente;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PrenotazioneRicercaRespDTO(UUID prenotazioneId, LocalDate arrivo, LocalDate partenza, double totalePrezzo,
                                         Utente utente, Preventivo preventivo, List<Camera> camere) {
}
