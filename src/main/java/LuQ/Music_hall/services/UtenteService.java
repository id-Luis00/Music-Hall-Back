package LuQ.Music_hall.services;

import LuQ.Music_hall.entities.Ruolo;
import LuQ.Music_hall.entities.Sala;
import LuQ.Music_hall.entities.Utente;
import LuQ.Music_hall.enums.TipoMusicista;
import LuQ.Music_hall.exceptions.BadRequestException;
import LuQ.Music_hall.exceptions.NotFoundException;
import LuQ.Music_hall.payloads.*;
import LuQ.Music_hall.repositories.SalaRepository;
import LuQ.Music_hall.repositories.UtenteRepository;
import LuQ.Music_hall.security.JWTTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RuoloService ruoloService;

    @Autowired
    private SalaService salaService;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jwtTools;


    // aggiungere metodi per: Salvare, Cercare, Modificare, Eliminare


    // metodo di salvataggio

    public NewUtenteRespDTO saveNewUtente(NewUtenteDTO body) {
        // controlli se già esiste l'utente

        // controllo dell'email
        if (this.utenteRepository.existsByEmail(body.email())) throw new BadRequestException("L'email è già esistente, riprova con un'altra email");

        // controllo dello username
        if (this.utenteRepository.existsByUsername(body.username())) throw new BadRequestException("Questo username è già in utilizzo");


        // se non c'è nessun utente così procedo con la creazione dell'utente
        Utente utente = new Utente(
                body.nome(),
                body.cognome(),
                body.username(),
                body.email(),
                bcrypt.encode(body.password()),
                body.dataDiNascita(),
                TipoMusicista.valueOf(body.tipoMusicista()));

        // assegno l'avatar standard
        utente.setAvatar("https://ui-avatars.com/api/?name="+body.nome()+"+"+body.cognome());

        // assegno il ruolo all'utente
        // creo il ruolo UTENTE e ADMIN

        // controllo se il ruolo UTENTE esiste già, sennò lo creo
        if (this.ruoloService.existsByNomeRuolo("UTENTE")) {
            //continua senza creare il ruolo UTENTE, ma lo assegno
            Ruolo roleFound = this.ruoloService.findByNomeRuolo("UTENTE");
            roleFound.addUsertoRole(utente);

            // aggiungo il ruolo all'utente
            utente.addRoleToUser(roleFound);

        } else {
            // crea il ruolo utente
            Ruolo defaultRole = new Ruolo("UTENTE");
            utente.addRoleToUser(defaultRole);
            defaultRole.addUsertoRole(utente);
            Ruolo admin = new Ruolo("ADMIN");
            this.ruoloService.saveRuolo(admin);
            this.ruoloService.saveRuolo(defaultRole);
        }

        // salvo utente con repository
        this.utenteRepository.save(utente);

        // loggo il fatto che sia andato tutto bene e stampo sulla console il nuovo utente
        log.info("nuovo utente creato!");
        log.info(String.valueOf(utente));

        return new NewUtenteRespDTO(utente.getId());

    }

    // metodo per salvare un nuovo utente "PROPRIETARIO"
    public NewOwnerRespDTO saveNewOwner(NewOwnerDTO body) {
        // controlli preliminari

        // controllo se l'email è già in uso
        if (this.utenteRepository.existsByEmail(body.email())) throw new BadRequestException("L'email è già in uso");

        // creo il nuovo utente
        Utente owner = new Utente(
                body.nome(),
                body.cognome(),
                body.email(),
                bcrypt.encode(body.password()),
                body.dataDiNascita()
        );

        // assegno un avatar di default
        owner.setAvatar("https://ui-avatars.com/api/?name="+body.nome()+"+"+body.cognome());

        // Devo assegnare a questo nuovo utente il ruolo di "PROPRIETARIO"
        // come con UTENTE, se non esiste già, lo creerò da zero. Sennò utilizzerò quello esistente
        if ( this.ruoloService.existsByNomeRuolo("PROPRIETARIO")) {
            Ruolo roleFound = this.ruoloService.findByNomeRuolo("PROPRIETARIO");
            roleFound.addUsertoRole(owner);
            owner.addRoleToUser(roleFound);
        } else {
            Ruolo ownerRole = new Ruolo("PROPRIETARIO");
            ownerRole.addUsertoRole(owner);
            owner.addRoleToUser(ownerRole);
            this.ruoloService.saveRuolo(ownerRole);

        }

        // finito di aggiungere il ruolo e aggiungerlo ai ruoli del proprietario, salviamo il nuovo proprietario
        this.utenteRepository.save(owner);

        // loggo il fatto che il nuovo proprietario sia stato aggiunto
        log.info("Proprietario aggiunto con successo!");
        log.info(String.valueOf(owner));


        // ritorno il response
        return new NewOwnerRespDTO(owner.getId());

    }


    // metodo per stampare tutti gli utenti
    public Page<Utente> printAllUsers(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return this.utenteRepository.findAll(pageable);
    }

    // trova un utente tramite email
    public Utente findByEmail(String email) {
        return this.utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente non trovato, con email: " + email));
    }


    // trova utente per id
    public Utente findById (String id) {
        return this.utenteRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Utente non  trovato, con id: " + id));
    }

    // metodo per aggiungere una sala ai preferiti
    public void addSalaToPreferiti (AddToFavouritesDTO dtoFavourite) {

        // cerchiamo l'utente
        Utente utente = this.findById(String.valueOf(dtoFavourite.idUtente()));

        // cerchiamo la sala
        Sala sala = this.salaService.findById(String.valueOf(dtoFavourite.idSala()));

        // aggiungo la sala ai preferiti dell'utente
        utente.getListaSalePreferite().add(sala);

        // aggiungo l'utente alla lista degli utenti a cui è stata salvata come preferita
        sala.getListaUtenti().add(utente);

        // salvo il tutto
        this.utenteRepository.save(utente);
        this.salaRepository.save(sala);

    }
}
