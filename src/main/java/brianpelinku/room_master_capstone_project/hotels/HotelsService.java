package brianpelinku.room_master_capstone_project.hotels;

import brianpelinku.room_master_capstone_project.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HotelsService {

    @Autowired
    private HotelsRepository hotelsRepository;

    public Hotel findById(UUID hotelId) {
        return this.hotelsRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel con id " + hotelId + " non trovato."));
    }

    public Page<Hotel> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.hotelsRepository.findAll(pageable);
    }
}
