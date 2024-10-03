package brianpelinku.room_master_capstone_project.auths;

import brianpelinku.room_master_capstone_project.exceptions.Validation;
import brianpelinku.room_master_capstone_project.utenti.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private AuthService authService;

    @Autowired
    private Validation validation;

    @PostMapping("/login")
    public UtentiLoginRespDTO login(@RequestBody @Validated UtentiLoginDTO body, BindingResult validation) {
        this.validation.validate(validation);
        return new UtentiLoginRespDTO(this.authService.checkCredenzialiAndGeneraToken(body));
    }

    @PostMapping("/register")
    public UtentiRespDTO registraUtente(@RequestBody @Validated UtentiDTO body, BindingResult validation) {
        this.validation.validate(validation);
        return new UtentiRespDTO(this.utentiService.save(body).utenteId());
    }
}
