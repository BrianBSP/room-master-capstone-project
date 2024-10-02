package brianpelinku.room_master_capstone_project.preventivi;

import brianpelinku.room_master_capstone_project.enums.PeriodoSoggiorno;
import brianpelinku.room_master_capstone_project.enums.TipoCamera;
import brianpelinku.room_master_capstone_project.enums.TipoServizio;
import brianpelinku.room_master_capstone_project.listiniPrezzi.ListinoPrezzi;
import brianpelinku.room_master_capstone_project.prenotazioni.Prenotazione;
import brianpelinku.room_master_capstone_project.utenti.Utente;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "preventivi")
@Getter
@Setter
@NoArgsConstructor
public class Preventivo {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private LocalDate arrivo;
    private LocalDate partenza;
    @Column(name = "tipo_camera")
    @Enumerated(EnumType.STRING)
    private TipoCamera tipoCamera;
    @Column(name = "tipo_servizio")
    @Enumerated(EnumType.STRING)
    private TipoServizio tipoServizio;
    @Column(name = "periodo_soggiorno")
    @Enumerated(EnumType.STRING)
    private PeriodoSoggiorno periodoSoggiorno;
    @Column(name = "numero_adulti")
    private int numeroAdulti;
    @Column(name = "numero_bambini")
    private int numeroBambini;
    @Column(name = "totale_prezzo_preventivo")
    private double totalePrezzoPreventivo;
    private boolean accettato;

    @ManyToOne
    @JoinColumn(name = "utenteId")
    private Utente utente;

    @OneToOne(mappedBy = "preventivo")
    private Prenotazione prenotazione;

    @ManyToOne
    @JoinColumn(name = "listinoPrezziId")
    private ListinoPrezzi listinoPrezzi;

    // COSTRUTTORI


    public Preventivo(LocalDate arrivo, LocalDate partenza, TipoCamera tipoCamera, TipoServizio tipoServizio, PeriodoSoggiorno periodoSoggiorno, int numeroAdulti, int numeroBambini, double totalePrezzoPreventivo, boolean accettato) {
        this.arrivo = arrivo;
        this.partenza = partenza;
        this.tipoCamera = tipoCamera;
        this.tipoServizio = tipoServizio;
        this.periodoSoggiorno = periodoSoggiorno;
        this.numeroAdulti = numeroAdulti;
        this.numeroBambini = numeroBambini;
        this.totalePrezzoPreventivo = totalePrezzoPreventivo;
        this.accettato = accettato;
    }
}
