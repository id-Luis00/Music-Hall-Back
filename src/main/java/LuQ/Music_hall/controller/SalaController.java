package LuQ.Music_hall.controller;

import LuQ.Music_hall.entities.Sala;
import LuQ.Music_hall.entities.SalaSpec;
import LuQ.Music_hall.entities.Utente;
import LuQ.Music_hall.payloads.NewSalaDTO;
import LuQ.Music_hall.payloads.NewSalaRespDTO;
import LuQ.Music_hall.repositories.SalaRepository;
import LuQ.Music_hall.services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sale")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @Autowired
    private SalaRepository salaRepository;


    @PostMapping("/register")
    @PreAuthorize("hasAnyAuthority('PROPRIETARIO', 'ADMIN')")
    public NewSalaRespDTO saveNewSala(@RequestBody NewSalaDTO body, @AuthenticationPrincipal Utente proprietario) {
        return this.salaService.saveNewSala(body, proprietario);
    }

    // endpoint per creare sale senza associare il proprietario da subito
    @PostMapping("/nonProperty")
    @PreAuthorize("hasAnyAuthority('PROPRIETARIO', 'ADMIN')")
    public NewSalaRespDTO saveNewSalaSenzaProprietario(@RequestBody NewSalaDTO body) {
        return this.salaService.saveNewSalaSenzaProprietario(body);
    }

    // metodo per associare un proprietario alla sala
    @PostMapping("/{salaId}")
    @PreAuthorize("hasAnyAuthority('PROPRIETARIO', 'ADMIN')")
    public ResponseEntity<String> associaProprietario(@PathVariable UUID salaId, @AuthenticationPrincipal Utente proprietario) {
        this.salaService.associaProprietario(salaId, proprietario);
        return ResponseEntity.ok("La sala è stata associata con successo a: " + proprietario.getNome() + " " + proprietario.getCognome());
    }

    @GetMapping
    public Page<Sala> findAllSale(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "50") int size,
                                  @RequestParam(defaultValue = "nomeSala") String sortBy,
                                  @RequestParam(required = false) String nomeSala,
                                  @RequestParam(required = false) String comune,
                                  @RequestParam(required = false) String regione,
                                  @RequestParam(required = false) String indirizzo,
                                  @RequestParam(required = false) Integer capienza,
                                  @RequestParam(required = false) Double prezzoOrario,
                                  @RequestParam(required = false) String cap) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Specification<Sala> spec = Specification.where(null);

        if (nomeSala != null) {
            spec = spec.and(SalaSpec.hasNomeSala(nomeSala));
        }
        if (comune != null) {
            spec = spec.and(SalaSpec.hasComune(comune));
        }
        if (regione != null) {
            spec = spec.and(SalaSpec.hasRegione(regione));
        }
        if (indirizzo != null) {
            spec = spec.and(SalaSpec.hasIndirizzo(indirizzo));
        }
        if (capienza != null) {
            spec = spec.and(SalaSpec.hasCapienza(capienza));
        }
        if (prezzoOrario != null) {
            spec = spec.and(SalaSpec.hasPrezzoOrario(prezzoOrario));
        }
        if (cap != null) {
            spec = spec.and(SalaSpec.hasCap(cap));
        }

        return this.salaRepository.findAll(spec, pageable);
    }



    @GetMapping("/auth/{idSala}")
    public Sala findSalaById(@PathVariable String idSala) {
        return this.salaService.findById(idSala);
    }

    @PutMapping("/{idSala}")
    public void findAndUpdate(@PathVariable UUID idSala, @RequestBody NewSalaDTO body) {
        this.salaService.findAndUpdate(idSala, body);
    }

//    @DeleteMapping("/{idSala}")
//    @PreAuthorize("hasAnyAuthority('PROPRIETARIO')")
//    public void findAndDeleteSala(@PathVariable UUID idSala) {
//        this.salaService.findAndDeleteSala(nomeSala);
//    }

}
