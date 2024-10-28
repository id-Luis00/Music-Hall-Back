package LuQ.Music_hall.controller;


import LuQ.Music_hall.entities.Utente;
import LuQ.Music_hall.payloads.AddToFavouritesDTO;
import LuQ.Music_hall.payloads.NewUtenteDTO;
import LuQ.Music_hall.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping
    // @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Utente> findAllUsers(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size){
        return this.utenteService.printAllUsers(page, size);
    }

    // endpoint per reperire tutti i propri dati utente
    @GetMapping("/me")
    public Utente findByToken(@AuthenticationPrincipal Utente utenteAutenticato) {
        return utenteAutenticato;
    }

    // endpoint per modificare account
    @PutMapping("{idUtente}")
    public void findAndUpdate(@PathVariable UUID idUtente, @RequestBody NewUtenteDTO body) {
        this.utenteService.findAndUpdate(idUtente, body);
    }

    // endpoint per modificare account
    @DeleteMapping("{idUtente}")
    public void findAndDelete(@PathVariable UUID idUtente) {
        this.utenteService.findAndDelete(idUtente);
    }

    // endpoint per inserire sala ai preferiti
    @PostMapping("/preferiti")
    @PreAuthorize("hasAnyAuthority('UTENTE')")
    public ResponseEntity<String> addSalaToPreferiti(@RequestBody AddToFavouritesDTO dtoFavourites) {
        this.utenteService.addSalaToPreferiti(dtoFavourites);
        return ResponseEntity.ok("Sala aggiunta ai preferiti con successo");
    }
}
