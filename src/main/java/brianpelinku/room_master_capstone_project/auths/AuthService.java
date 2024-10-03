package brianpelinku.room_master_capstone_project.auths;

import brianpelinku.room_master_capstone_project.exceptions.UnauthorizedException;
import brianpelinku.room_master_capstone_project.security.JWTTools;
import brianpelinku.room_master_capstone_project.utenti.Utente;
import brianpelinku.room_master_capstone_project.utenti.UtentiLoginDTO;
import brianpelinku.room_master_capstone_project.utenti.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredenzialiAndGeneraToken(UtentiLoginDTO body) {
        Utente trovato = this.utentiService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), trovato.getPassword())) {
            return jwtTools.createToken(trovato);
        } else {
            throw new UnauthorizedException("Credenziali ERRATE!");
        }
    }
}
