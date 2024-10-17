package brianpelinku.room_master_capstone_project.utenti;

import brianpelinku.room_master_capstone_project.exceptions.Validation;
import brianpelinku.room_master_capstone_project.preventivi.PreventiviService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtentiController {

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private Validation validation;

    @Autowired
    private PreventiviService preventiviService;

    @GetMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utente getById(@PathVariable UUID utenteId) {
        return this.utentiService.findById(utenteId);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Utente> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.utentiService.findAll(page, size, sortBy);
    }

    @DeleteMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID utenteId) {
        this.utentiService.findByIdAndDelete(utenteId);
    }

    @PutMapping("/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UtentiRespDTO findByIdAndUpdate(@PathVariable UUID utenteId, @RequestBody @Validated UtentiDTO body) {
        return this.utentiService.findByIdAndUpdate(utenteId, body);
    }

    @GetMapping("/me")
    public Utente getProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        return utenteCorrenteAutenticato;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato) {
        this.utentiService.findByIdAndDelete(utenteCorrenteAutenticato.getId());
    }

    @PutMapping("/me")
    public UtentiRespDTO updateProfile(@AuthenticationPrincipal Utente utenteCorrenteAutenticato, @RequestBody @Validated UtentiDTO body) {
        return this.utentiService.findByIdAndUpdate(utenteCorrenteAutenticato.getId(), body);
    }

    @PostMapping("/me/avatar")
    public Utente updateAvatar(@AuthenticationPrincipal Utente utenteCorrenteAutenticato, @RequestParam("avatar") MultipartFile img) {
        try {
            return this.utentiService.uploadImagine(img, utenteCorrenteAutenticato.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{nome}-{cognome}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Utente> findByNomeAndCognome(@PathVariable("nome") String nome, @PathVariable("cognome") String cognome) {
        return this.utentiService.findByNomeAndCognome(nome, cognome);
    }


    // CLOUDINARY
    // UPLOAD IMMAGINE
    @PostMapping("/{utenteId}/avatar")
    public Utente uploadImage(@RequestParam("avatar") MultipartFile img, @PathVariable UUID utenteId) throws IOException {
        try {
            return this.utentiService.uploadImagine(img, utenteId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
