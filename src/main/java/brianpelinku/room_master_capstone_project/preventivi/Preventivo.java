package brianpelinku.room_master_capstone_project.preventivi;

import brianpelinku.room_master_capstone_project.enums.PeriodoSoggiorno;
import brianpelinku.room_master_capstone_project.enums.TipoCamera;
import brianpelinku.room_master_capstone_project.enums.TipoServizio;
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


}
