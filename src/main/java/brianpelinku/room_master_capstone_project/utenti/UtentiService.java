package brianpelinku.room_master_capstone_project.utenti;

import brianpelinku.room_master_capstone_project.exceptions.BadRequestException;
import brianpelinku.room_master_capstone_project.exceptions.NotFoundException;
import brianpelinku.room_master_capstone_project.tools.MailSander;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UtentiService {
    @Autowired
    private UtentiRepository utentiRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private MailSander mailSander;

    public Utente findByEmail(String email) {
        return utentiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email non trovata."));
    }

    public UtentiRespDTO save(UtentiDTO body) {
        // verifico se l'email è già stato utilizzata
        this.utentiRepository.findByEmail(body.email()).ifPresent(author -> {
            throw new BadRequestException("L'email " + body.email() + " è già in uso.");
        });

        Utente nuovo = new Utente(body.nome(), body.cognome(), body.email(), bcrypt.encode(body.password()),
                "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome());

        // salvo il nuovo record
        Utente utenteSalvato = this.utentiRepository.save(nuovo);

        // invio email conferma registrazione
        mailSander.sendRegistrationEmail(utenteSalvato);

        return new UtentiRespDTO(utenteSalvato.getId());
    }

    public Utente findById(UUID utenteId) {
        return this.utentiRepository.findById(utenteId)
                .orElseThrow(() -> new NotFoundException("L'utente con l'id " + utenteId + " non è stato trovato."));
    }

    // upload immagine
    public Utente uploadImagine(MultipartFile file, UUID utenteId) throws IOException {
        Utente trovato = this.findById(utenteId);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");

        trovato.setAvatar(url);

        return utentiRepository.save(trovato);
    }

    // cerco tutti gli utenti
    public Page<Utente> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.utentiRepository.findAll(pageable);
    }

    // delete utente
    public void findByIdAndDelete(UUID utenteId) {
        Utente trovato = this.findById(utenteId);
        this.utentiRepository.delete(trovato);
    }

    // update utente
    public UtentiRespDTO findByIdAndUpdate(UUID utenteId, UtentiDTO body) {

        this.utentiRepository.findByEmail(body.email()).ifPresent(author -> {
            if (!author.getId().equals(utenteId)) {
                throw new BadRequestException("L'email " + body.email() + " è già in uso.");
            }
        });

        Utente trovato = this.findById(utenteId);
        trovato.setEmail(body.email());
        trovato.setPassword(bcrypt.encode(body.password()));
        trovato.setNome(body.nome());
        trovato.setCognome(body.cognome());


        // salvo il nuovo record
        return new UtentiRespDTO(this.utentiRepository.save(trovato).getId());
    }

}
