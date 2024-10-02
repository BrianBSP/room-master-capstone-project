package brianpelinku.room_master_capstone_project.camere;

import brianpelinku.room_master_capstone_project.enums.TipoCamera;
import brianpelinku.room_master_capstone_project.hotels.Hotel;
import brianpelinku.room_master_capstone_project.prenotazioni.Prenotazione;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "camere")
@Getter
@Setter
@NoArgsConstructor
public class Camera {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private int numeroCamera;
    private int capienzaCamera;
    @Enumerated(EnumType.STRING)
    private TipoCamera tipoCamera;

    @ManyToOne
    @JoinColumn(name = "prenotazioneId")
    private Prenotazione prenotazione;

    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;


}
