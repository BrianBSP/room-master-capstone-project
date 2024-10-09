package brianpelinku.room_master_capstone_project.hotels;

import brianpelinku.room_master_capstone_project.exceptions.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelsService hotelsService;

    @Autowired
    private Validation validation;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Hotel> getAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size,
                              @RequestParam(defaultValue = "id") String sortBy) {
        return this.hotelsService.findAll(page, size, sortBy);
    }

    @GetMapping("/{hotelId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Hotel findById(@PathVariable UUID hotelId) {
        return this.hotelsService.findById(hotelId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public HotelRespDTO save(@RequestBody @Validated HotelDTO body, BindingResult validation) {
        this.validation.validate(validation);
        return new HotelRespDTO(this.hotelsService.save(body).hotelId());
    }

    @DeleteMapping("/{hotelId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID hotelId) {
        this.hotelsService.findByIdAndDelete(hotelId);
    }


}
