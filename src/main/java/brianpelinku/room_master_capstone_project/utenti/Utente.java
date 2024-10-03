package brianpelinku.room_master_capstone_project.utenti;

import brianpelinku.room_master_capstone_project.enums.RuoloUtente;
import brianpelinku.room_master_capstone_project.preventivi.Preventivo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"password", "clientiList", "enabled", "accountNonLocked", "accountNonExpired", "credentialsNonExpired", "authorities"})
public class Utente implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String avatar;
    @Column(name = "ruolo_utente")
    @Enumerated(EnumType.STRING)
    private RuoloUtente ruoloUtente;

    @OneToMany(mappedBy = "utente")
    @JsonIgnore
    private List<Preventivo> preventivoList;


    // COSTRUTTORI

    public Utente(String nome, String cognome, String email, String password, String avatar) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.ruoloUtente = RuoloUtente.UTENTE;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruoloUtente.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
