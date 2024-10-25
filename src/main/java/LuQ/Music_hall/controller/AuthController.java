package LuQ.Music_hall.controller;

import LuQ.Music_hall.payloads.*;
import LuQ.Music_hall.security.Validation;
import LuQ.Music_hall.services.AuthService;
import LuQ.Music_hall.services.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private Validation validation;
    @Autowired
    private AuthService authService;

    // endpoint di registrazione utente
    @PostMapping("/utenti/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUtenteRespDTO saveNewUtente(@RequestBody @Valid NewUtenteDTO body, BindingResult validation) {
        this.validation.validate(validation);
        return this.utenteService.saveNewUtente(body);
    }

    // endpoint per creare un nuovo utente con ruolo "PROPRIETARIO"
    @PostMapping("/owner/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewOwnerRespDTO saveNewOwner(@RequestBody @Valid NewOwnerDTO body, BindingResult validation) {
        this.validation.validate(validation);
        return this.utenteService.saveNewOwner(body);
    }

    // login degli utenti
    @PostMapping("/utenti/login")
    public UtenteLoginRespDTO findAndCreateToken(@RequestBody UtenteLoginDTO body) {
        return this.authService.checkAndCreateToken(body);
    }




}
