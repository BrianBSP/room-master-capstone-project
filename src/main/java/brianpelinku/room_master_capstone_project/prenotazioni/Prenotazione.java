package brianpelinku.room_master_capstone_project.prenotazioni;

import brianpelinku.room_master_capstone_project.preventivi.Preventivo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    @Column(name = "giorni_di_pernottamento")
    private int giorniDiPernottamento;

    @OneToOne
    @JoinColumn(name = "preventivoId")
    private Preventivo preventivo;

}
