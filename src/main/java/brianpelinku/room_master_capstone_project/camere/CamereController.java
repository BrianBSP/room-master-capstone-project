package brianpelinku.room_master_capstone_project.camere;

import brianpelinku.room_master_capstone_project.exceptions.Validation;
import brianpelinku.room_master_capstone_project.hotels.Hotel;
import brianpelinku.room_master_capstone_project.hotels.HotelsService;
import brianpelinku.room_master_capstone_project.prenotazioni.Prenotazione;
import brianpelinku.room_master_capstone_project.prenotazioni.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/camere")
public class CamereController {

    @Autowired
    private CamereService camereService;

    @Autowired
    private PrenotazioniService prenotazioniService;

    @Autowired
    private HotelsService hotelsService;

    @Autowired
    private Validation validation;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Camera> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.camereService.findAll(page, size, sortBy);
    }

    @GetMapping("/prenotazioni/{prenotazioneId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Camera> findByPrenotazione(@PathVariable UUID prenotazioneId) {
        Prenotazione trovate = this.prenotazioniService.findById(prenotazioneId);
        return this.camereService.findByPrenotazione(trovate);
    }

    @GetMapping("/{cameraId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Camera findById(@PathVariable UUID cameraId) {
        return this.camereService.findById(cameraId);
    }

    @GetMapping("/{numeroCamera}/{prenotazioneId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Camera findByNumeroCameraAndPrenotazione(@PathVariable int numeroCamera, @PathVariable UUID prenotazioneId) {
        Prenotazione trovata = this.prenotazioniService.findById(prenotazioneId);
        return this.camereService.findByNumeroCameraAndPrenotazione(numeroCamera, trovata);
    }

    @GetMapping("/{numeroCamera}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Camera findByNumeroCamera(@PathVariable int numeroCamera) {
        return this.camereService.findByNumeroCamera(numeroCamera);
    }

    @GetMapping("/hotels")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Camera> findByHotel(Hotel hotel) {
        return this.camereService.findByHotel(hotel);
    }

    @GetMapping()


    @PostMapping("/{hotelId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public CamereRespDTO save(@RequestBody @Validated CamereDTO body, @PathVariable UUID hotelId, BindingResult validation) {
        this.validation.validate(validation);
        Hotel trovato = this.hotelsService.findById(hotelId);
        return this.camereService.save(body, trovato);
    }

    @PutMapping("/{cameraId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CamereRespDTO findByIdAndUpdate(@RequestBody @Validated CamereDTO body, @PathVariable UUID cameraId) {
        return new CamereRespDTO(this.camereService.findByIdAndUpdate(cameraId, body).cameraId());
    }

    @DeleteMapping("/{cameraId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID cameraId) {
        this.camereService.findByIdAndDelete(cameraId);
    }

}
