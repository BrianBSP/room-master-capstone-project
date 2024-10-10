package brianpelinku.room_master_capstone_project.camere;

import brianpelinku.room_master_capstone_project.enums.StatoCamera;
import brianpelinku.room_master_capstone_project.enums.TipoCamera;
import brianpelinku.room_master_capstone_project.hotels.Hotel;
import brianpelinku.room_master_capstone_project.prenotazioni.Prenotazione;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "numero_camera")
    private int numeroCamera;
    @Column(name = "capienza_camera")
    private int capienzaCamera;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_camera")
    private TipoCamera tipoCamera;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_camera")
    private StatoCamera statoCamera;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "prenotazioneId")
    private Prenotazione prenotazione;

    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;


    public Camera(int numeroCamera, int capienzaCamera, TipoCamera tipoCamera) {
        this.numeroCamera = numeroCamera;
        this.capienzaCamera = capienzaCamera;
        this.tipoCamera = tipoCamera;
    }
}
