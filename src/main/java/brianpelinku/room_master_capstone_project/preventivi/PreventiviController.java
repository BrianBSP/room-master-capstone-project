package brianpelinku.room_master_capstone_project.preventivi;

import brianpelinku.room_master_capstone_project.utenti.Utente;
import brianpelinku.room_master_capstone_project.utenti.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/preventivi")
public class PreventiviController {

    @Autowired
    private PreventiviService preventiviService;

    @Autowired
    private UtentiService utentiService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Preventivo> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.preventiviService.findAll(page, size, sortBy);
    }

    @GetMapping("/utente/{utenteId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Preventivo> findByUtente(@PathVariable UUID utenteId) {
        Utente trovato = this.utentiService.findById(utenteId);
        return this.preventiviService.findByUtente(trovato);
    }

    @GetMapping("/anno/{anno}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Preventivo> findByAnno(@PathVariable int anno) {
        return this.preventiviService.findByAnno(anno);
    }


}
