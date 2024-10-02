package brianpelinku.room_master_capstone_project.listiniPrezzi;

import brianpelinku.room_master_capstone_project.enums.PeriodoSoggiorno;
import brianpelinku.room_master_capstone_project.enums.TipoCamera;
import brianpelinku.room_master_capstone_project.enums.TipoServizio;
import brianpelinku.room_master_capstone_project.hotels.Hotel;
import brianpelinku.room_master_capstone_project.preventivi.Preventivo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "listini_prezzi")
@Getter
@Setter
@NoArgsConstructor
public class ListinoPrezzi {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "periodo_soggiorno")
    @Enumerated(EnumType.STRING)
    private PeriodoSoggiorno periodoSoggiorno;
    @Column(name = "tipo_camera")
    @Enumerated(EnumType.STRING)
    private TipoCamera tipoCamera;
    @Column(name = "tipo_servizio")
    @Enumerated(EnumType.STRING)
    private TipoServizio tipoServizio;
    @Column(name = "prezzo_adulto_notte")
    private double prezzoAdultoNotte;
    @Column(name = "prezzo_bambino_notte")
    private double prezzoBambinoNotte;

    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;

    @OneToMany(mappedBy = "listinoPrezzi")
    private List<Preventivo> preventivoList;

    // COSTRUTTORI

    public ListinoPrezzi(PeriodoSoggiorno periodoSoggiorno, TipoCamera tipoCamera, TipoServizio tipoServizio, double prezzoAdultoNotte, double prezzoBambinoNotte) {
        this.periodoSoggiorno = periodoSoggiorno;
        this.tipoCamera = tipoCamera;
        this.tipoServizio = tipoServizio;
        this.prezzoAdultoNotte = prezzoAdultoNotte;
        this.prezzoBambinoNotte = prezzoBambinoNotte;
    }
}
