package brianpelinku.room_master_capstone_project.preventivi;

import brianpelinku.room_master_capstone_project.enums.PeriodoSoggiorno;
import brianpelinku.room_master_capstone_project.enums.TipoCamera;
import brianpelinku.room_master_capstone_project.enums.TipoServizio;
import brianpelinku.room_master_capstone_project.exceptions.BadRequestException;
import brianpelinku.room_master_capstone_project.exceptions.NotFoundException;
import brianpelinku.room_master_capstone_project.listiniPrezzi.ListinoPrezzi;
import brianpelinku.room_master_capstone_project.prenotazioni.Prenotazione;
import brianpelinku.room_master_capstone_project.utenti.Utente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
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
    private LocalDate data;
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
    @JsonIgnore
    @JoinColumn(name = "utenteId")
    private Utente utente;

    @OneToOne(mappedBy = "preventivo")
    @JsonIgnore
    private Prenotazione prenotazione;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "listinoPrezziId")
    private ListinoPrezzi listinoPrezzi;

    // COSTRUTTORI


    public Preventivo(LocalDate arrivo, LocalDate partenza, TipoCamera tipoCamera, TipoServizio tipoServizio, PeriodoSoggiorno periodoSoggiorno, int numeroAdulti, int numeroBambini, double totalePrezzoPreventivo) {
        this.data = LocalDate.now();
        this.arrivo = arrivo;
        this.partenza = partenza;
        this.tipoCamera = tipoCamera;
        this.tipoServizio = tipoServizio;
        this.periodoSoggiorno = periodoSoggiorno;
        this.numeroAdulti = numeroAdulti;
        this.numeroBambini = numeroBambini;
        this.totalePrezzoPreventivo = totalePrezzoPreventivo;
        this.accettato = false;
    }

    // METODI

    public double calcolaTotalePreventivo(List<ListinoPrezzi> listinoPrezzi) {

        Optional<ListinoPrezzi> prezzoListino = listinoPrezzi
                .stream()
                .filter(lp -> lp.getTipoCamera() == this.tipoCamera &&
                        lp.getTipoServizio() == this.tipoServizio &&
                        lp.getPeriodoSoggiorno() == this.periodoSoggiorno).findFirst();

        if (prezzoListino.isPresent()) {
            ListinoPrezzi prezzo = prezzoListino.get();

            double totAdulti = prezzo.getPrezzoAdultoNotte() * this.numeroAdulti;
            double totBambini = prezzo.getPrezzoBambinoNotte() * this.numeroBambini;
            double totGiornaliero = totAdulti + totBambini;

            long numeroNotti = ChronoUnit.DAYS.between(this.arrivo, this.partenza);

            this.totalePrezzoPreventivo = totGiornaliero * numeroNotti;
        } else {
            throw new NotFoundException("Prezzo non trovato nel listino");
        }
        return this.totalePrezzoPreventivo;
    }

    public PeriodoSoggiorno determinaPeriodo(LocalDate dataArrivo) {
        int mese = dataArrivo.getMonthValue();

        if (mese == 11 || mese == 12 || mese <= 3) {
            return PeriodoSoggiorno.BASSA_STAGIONE;
        } else if (mese <= 5 || mese >= 9) {
            return PeriodoSoggiorno.MEDIA_STAGIONE;
        } else if (mese >= 6 && mese <= 8) {
            return PeriodoSoggiorno.ALTA_STAGIONE;
        } else {
            throw new BadRequestException("Inserire un mese valido.");
        }
    }


}
