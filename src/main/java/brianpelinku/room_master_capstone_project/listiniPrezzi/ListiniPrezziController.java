package brianpelinku.room_master_capstone_project.listiniPrezzi;

import brianpelinku.room_master_capstone_project.exceptions.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listini")
public class ListiniPrezziController {

    @Autowired
    private ListiniPrezziService listiniPrezziService;

    @Autowired
    private Validation validation;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<ListinoPrezzi> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return this.listiniPrezziService.findAll(page, size, sortBy);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ListiniRespDTO save(@RequestBody @Validated ListiniDTO body, BindingResult validation) {
        this.validation.validate(validation);
        return new ListiniRespDTO(this.listiniPrezziService.save(body).listinoId());
    }
}
