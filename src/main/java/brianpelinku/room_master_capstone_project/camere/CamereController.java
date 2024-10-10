package brianpelinku.room_master_capstone_project.camere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/camere")
public class CamereController {

    @Autowired
    private CamereService camereService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Camera> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.camereService.findAll(page, size, sortBy);
    }
}
