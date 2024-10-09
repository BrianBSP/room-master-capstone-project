package brianpelinku.room_master_capstone_project.hotels;

import brianpelinku.room_master_capstone_project.enums.LivelloStruttura;
import brianpelinku.room_master_capstone_project.exceptions.NotFoundException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class HotelsService {

    @Autowired
    private HotelsRepository hotelsRepository;

    @Autowired
    private Cloudinary cloudinary;

    public Hotel findById(UUID hotelId) {
        return this.hotelsRepository.findById(hotelId).orElseThrow(() -> new NotFoundException("Hotel con id " + hotelId + " non trovato."));
    }

    public Page<Hotel> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.hotelsRepository.findAll(pageable);
    }

    public void findByIdAndDelete(UUID hotelId) {
        Hotel hotel = this.findById(hotelId);
        this.hotelsRepository.delete(hotel);
    }

    public HotelRespDTO save(HotelDTO body) {
        Hotel hotel = new Hotel();
        hotel.setNome(body.nome());
        hotel.setLivelloStruttura(LivelloStruttura.valueOf(body.livelloStruttura()));
        hotel.setNumeroPiani(body.numeroPiani());
        hotel.setNumeroCamere(body.numeroCamere());
        hotel.setLogoAziendale("https://ui-avatars.com/api/?name=hotel" + "+" + body.nome());
        return new HotelRespDTO(this.hotelsRepository.save(hotel).getId());
    }

    public HotelRespDTO findByIdAndUpdate(UUID hotelId, HotelDTO body) {
        Hotel trovato = this.findById(hotelId);
        trovato.setNome(body.nome());
        trovato.setLivelloStruttura(LivelloStruttura.valueOf(body.livelloStruttura()));
        trovato.setNumeroPiani(body.numeroPiani());
        trovato.setNumeroCamere(body.numeroCamere());
        return new HotelRespDTO(this.hotelsRepository.save(trovato).getId());
    }

    public HotelRespDTO findByIdAndUploadImage(UUID hotelId, MultipartFile file) throws IOException {
        Hotel trovato = this.findById(hotelId);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");

        trovato.setLogoAziendale(url);
        return new HotelRespDTO(this.hotelsRepository.save(trovato).getId());
    }

}
