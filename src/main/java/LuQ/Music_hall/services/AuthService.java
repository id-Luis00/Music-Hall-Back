package LuQ.Music_hall.services;


import LuQ.Music_hall.entities.Utente;
import LuQ.Music_hall.exceptions.BadRequestException;
import LuQ.Music_hall.payloads.UtenteLoginDTO;
import LuQ.Music_hall.payloads.UtenteLoginRespDTO;
import LuQ.Music_hall.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private  UtenteService utenteService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jwtTools;

    // checkAndCreateToken
    public UtenteLoginRespDTO checkAndCreateToken(UtenteLoginDTO body) {

        // controlliamo che sia presente nel db la seguente email ed estrapoliamo l'utente
        // se trova l'utente lo inserisco in una istanza
        Utente utenteTrovato = this.utenteService.findByEmail(body.email());

        // creo il token
        if (bcrypt.matches(body.password(), utenteTrovato.getPassword())) return new UtenteLoginRespDTO(this.jwtTools.createToken(utenteTrovato));

        else throw new BadRequestException("Errore nella creazione del token");
    }
}
