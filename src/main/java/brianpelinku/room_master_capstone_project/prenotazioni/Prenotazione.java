package brianpelinku.room_master_capstone_project.prenotazioni;

import brianpelinku.room_master_capstone_project.camere.Camera;
import brianpelinku.room_master_capstone_project.preventivi.Preventivo;
import brianpelinku.room_master_capstone_project.utenti.Utente;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@NoArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private LocalDate arrivo;
    private LocalDate partenza;
    @Column(name = "totale_prezzo")
    private double totalePrezzo;

    @ManyToOne
    @JoinColumn(name = "utenteId")
    private Utente utente;

    @OneToOne
    @JoinColumn(name = "preventivoId")
    private Preventivo preventivo;

    @OneToMany(mappedBy = "prenotazione")
    private List<Camera> camere;

    // COSTRUTTORI

    public Prenotazione(LocalDate arrivo, LocalDate partenza, double totalePrezzo, Utente utente, Preventivo preventivo) {
        this.arrivo = arrivo;
        this.partenza = partenza;
        this.totalePrezzo = totalePrezzo;
        this.utente = utente;
        this.preventivo = preventivo;
    }
}
