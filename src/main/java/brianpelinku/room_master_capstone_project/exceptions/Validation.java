package brianpelinku.room_master_capstone_project.exceptions;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

@Component
public class Validation {
    public void validate(BindingResult validation) {
        String messages = validation.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(". "));
        if (validation.hasErrors()) throw new BadRequestException("Errori nel Payload. " + messages);
    }
}
