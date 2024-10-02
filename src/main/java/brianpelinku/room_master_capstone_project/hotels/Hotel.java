package brianpelinku.room_master_capstone_project.hotels;

import brianpelinku.room_master_capstone_project.camere.Camera;
import brianpelinku.room_master_capstone_project.enums.LivelloStruttura;
import brianpelinku.room_master_capstone_project.listiniPrezzi.ListinoPrezzi;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "hotels")
@Getter
@Setter
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    @Column(name = "livello_struttura")
    @Enumerated(EnumType.STRING)
    private LivelloStruttura livelloStruttura;
    @Column(name = "numero_piani")
    private int numeroPiani;
    @Column(name = "numero_camere")
    private int numeroCamere;
    @Column(name = "logo_aziendale")
    private String logoAziendale;

    @JsonIgnore
    @OneToMany(mappedBy = "hotel")
    private List<Camera> cameraList;

    @JsonIgnore
    @OneToMany(mappedBy = "hotel")
    private List<ListinoPrezzi> listinoPrezziList;

}
