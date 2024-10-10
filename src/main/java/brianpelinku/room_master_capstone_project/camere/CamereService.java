package brianpelinku.room_master_capstone_project.camere;

import brianpelinku.room_master_capstone_project.enums.StatoCamera;
import brianpelinku.room_master_capstone_project.enums.TipoCamera;
import brianpelinku.room_master_capstone_project.exceptions.NotFoundException;
import brianpelinku.room_master_capstone_project.hotels.Hotel;
import brianpelinku.room_master_capstone_project.hotels.HotelsService;
import brianpelinku.room_master_capstone_project.prenotazioni.Prenotazione;
import brianpelinku.room_master_capstone_project.prenotazioni.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CamereService {

    @Autowired
    private CamereRepository camereRepository;

    @Autowired
    private PrenotazioniService prenotazioniService;

    @Autowired
    private HotelsService hotelsService;

    public Page<Camera> findAll(int page, int size, String sortBy) {
        if (page > 100) page = 100;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.camereRepository.findAll(pageable);
    }

    public List<Camera> findByPrenotazione(Prenotazione prenotazione) {
        Prenotazione trovato = this.prenotazioniService.findById(prenotazione.getId());
        return this.camereRepository.findByPrenotazione(trovato);
    }

    public Camera findById(UUID cameraId) {
        return this.camereRepository.findById(cameraId)
                .orElseThrow(() -> new NotFoundException("Camera con id " + cameraId + " npn trovato."));
    }

    public Camera findByNumeroCameraAndPrenotazione(int numeroCamera, Prenotazione prenotazione) {
        Prenotazione trovato = this.prenotazioniService.findById(prenotazione.getId());
        return this.camereRepository.findByNumeroCameraAndPrenotazione(numeroCamera, trovato);
    }

    public Camera findByNumeroCamera(int numeroCamera) {
        return this.camereRepository.findByNumeroCamera(numeroCamera);
    }

    public CamereRespDTO save(CamereDTO body, Hotel hotel) {
        Camera camera = new Camera();
        camera.setNumeroCamera(body.numeroCamera());
        camera.setCapienzaCamera(body.capienzaCamera());
        camera.setTipoCamera(TipoCamera.valueOf(body.tipoCamera()));
        camera.setStatoCamera(StatoCamera.valueOf(body.statoCamera()));
        camera.setHotel(hotel);
        return new CamereRespDTO(this.camereRepository.save(camera).getId());
    }

    public CamereRespDTO findByIdAndUpdate(UUID cameraId, CamereDTO body) {
        Camera trovato = this.findById(cameraId);
        trovato.setNumeroCamera(body.numeroCamera());
        trovato.setCapienzaCamera(body.capienzaCamera());
        trovato.setTipoCamera(TipoCamera.valueOf(body.tipoCamera()));
        trovato.setStatoCamera(StatoCamera.valueOf(body.statoCamera()));
        return new CamereRespDTO(this.camereRepository.save(trovato).getId());
    }

    public void findByIdAndDelete(UUID cameraId) {
        Camera trovato = this.findById(cameraId);
        this.camereRepository.delete(trovato);
    }

    public List<Camera> findByHotel(Hotel hotel) {
        return this.camereRepository.findByHotel(hotel);
    }

    public List<Camera> findCamerePerPrenotazione(TipoCamera tipoCamera, int adulti, int bambini) {
        int totPersone = adulti + bambini;
        List<Camera> camereDisponibili = this.camereRepository.findCamereDisponibili(tipoCamera);
        List<Camera> camereAssegnate = new ArrayList<>();
        int personeAssegnate = 0;
        for (Camera camera : camereDisponibili) {
            camereAssegnate.add(camera);
            personeAssegnate += camera.getCapienzaCamera();
            if (personeAssegnate >= totPersone) {
                break;
            }
        }
        if (personeAssegnate < totPersone) {
            throw new NotFoundException("Non ci sono abbastanza camere disponibili.");
        }
        return camereAssegnate;
    }

    public void assegnaCamere(List<Camera> camere, Prenotazione prenotazione) {
        camere.forEach(camera -> {
            camera.setPrenotazione(prenotazione);
            camera.setStatoCamera(StatoCamera.OCCUPATA);
            camereRepository.save(camera);
        });
    }
}