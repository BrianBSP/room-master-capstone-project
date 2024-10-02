package brianpelinku.room_master_capstone_project.utenti;

import brianpelinku.room_master_capstone_project.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtentiService {
    @Autowired
    private UtentiRepository utentiRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    /*@Autowired
    private Cloudinary cloudinary;*/

    public Utente findByEmail(String email) {
        return utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email non trovata."));
    }

}
