package brianpelinku.room_master_capstone_project.listiniPrezzi;

import brianpelinku.room_master_capstone_project.enums.PeriodoSoggiorno;
import brianpelinku.room_master_capstone_project.enums.TipoCamera;
import brianpelinku.room_master_capstone_project.enums.TipoServizio;
import brianpelinku.room_master_capstone_project.exceptions.BadRequestException;
import brianpelinku.room_master_capstone_project.exceptions.NotFoundException;
import brianpelinku.room_master_capstone_project.hotels.Hotel;
import brianpelinku.room_master_capstone_project.hotels.HotelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListiniPrezziService {

    @Autowired
    private ListiniPrezziRepository listiniPrezziRepository;

    @Autowired
    private HotelsService hotelsService;

    public ListinoPrezzi findById(UUID listinoId) {
        return this.listiniPrezziRepository.findById(listinoId)
                .orElseThrow(() -> new NotFoundException("Listino Prezzi con id " + listinoId + " non trovato."));
    }

    public Page<ListinoPrezzi> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.listiniPrezziRepository.findAll(pageable);
    }

    public void findByIdAndDelete(UUID listinoId) {
        ListinoPrezzi trovato = this.findById(listinoId);
        this.listiniPrezziRepository.delete(trovato);
    }

    public ListiniRespDTO save(ListiniDTO body) {

        Hotel hotel = this.hotelsService.findById(UUID.fromString(body.hotelId()));

        ListinoPrezzi listinoPrezzi = new ListinoPrezzi();
        listinoPrezzi.setTipoCamera(TipoCamera.valueOf(body.tipoCamera()));
        listinoPrezzi.setTipoServizio(TipoServizio.valueOf(body.tipoServizio()));
        listinoPrezzi.setPeriodoSoggiorno(PeriodoSoggiorno.valueOf(body.periodoSoggiorno()));
        if (body.prezzoAdultoNotte() < 0)
            throw new BadRequestException("Il prezzo 'Adulto per Notte' non può essere inferiore a 0.");

        if (body.prezzoBambinoNotte() < 0)
            throw new BadRequestException("Il prezzo 'Bambino per Notte' non può essere inferiore a 0.");
        listinoPrezzi.setPrezzoAdultoNotte(body.prezzoAdultoNotte());
        listinoPrezzi.setPrezzoBambinoNotte(body.prezzoBambinoNotte());
        listinoPrezzi.setHotel(hotel);

        return new ListiniRespDTO(this.listiniPrezziRepository.save(listinoPrezzi).getId());


    }

    public ListiniRespDTO findByIdAndUpdatePrezzi(UUID listinoId, ListiniDTO body) {
        ListinoPrezzi trovato = this.findById(listinoId);

        if (body.prezzoAdultoNotte() < 0)
            throw new BadRequestException("Il prezzo 'Adulto per Notte' non può essere inferiore a 0.");

        if (body.prezzoBambinoNotte() < 0)
            throw new BadRequestException("Il prezzo 'Bambino per Notte' non può essere inferiore a 0.");

        trovato.setPrezzoAdultoNotte(body.prezzoAdultoNotte());
        trovato.setPrezzoBambinoNotte(body.prezzoBambinoNotte());

        return new ListiniRespDTO(this.listiniPrezziRepository.save(trovato).getId());
    }
}
