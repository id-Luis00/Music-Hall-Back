package LuQ.Music_hall.services;

import LuQ.Music_hall.entities.Sala;
import LuQ.Music_hall.entities.Utente;
import LuQ.Music_hall.enums.TipoSala;
import LuQ.Music_hall.exceptions.BadRequestException;
import LuQ.Music_hall.exceptions.NotFoundException;
import LuQ.Music_hall.payloads.NewSalaDTO;
import LuQ.Music_hall.payloads.NewSalaRespDTO;
import LuQ.Music_hall.repositories.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;


    // metodo per salvare una nuova sala
    public NewSalaRespDTO saveNewSala(NewSalaDTO body, @AuthenticationPrincipal Utente proprietario) {
        // controlli preliminari

        // controllo se il nome della sala è già in uso
        if (this.salaRepository.existsByNomeSala(body.nomeSala())) throw new BadRequestException("Il nome della sala è già in uso");

        // creo l'oggetto sala
        // in questo caso lo creo con un proprietario
        Sala sala = new Sala(body.nomeSala(),
                body.indirizzo(),
                body.capienza(),
                TipoSala.valueOf(body.tipoSala()),
                body.prezzoOrario(),
                body.imageURL(),
                proprietario,
                body.comune(),
                body.regione(),
                body.cap(),
                body.telefono(),
                body.website()
                );

        // aggiungo la sala alla lista delle sale del proprietario
        proprietario.getListaSale().add(sala);

        // salvo la nuova sala
        this.salaRepository.save(sala);

        log.info(proprietario.getNome() + " " + proprietario.getCognome() + " ha appena creato una sala, con id: " + sala.getId());
        log.info("Nome Sala: " + sala.getNomeSala());
        log.info("Proprietario della sala: " + sala.getProprietario().getId());
        return new NewSalaRespDTO(sala.getId());
    }

    // metodo per salvare una sala senza proprietario
    public NewSalaRespDTO saveNewSalaSenzaProprietario(NewSalaDTO body) {
        // controlli vari

        // controllo del nome se esiste già
        if (this.salaRepository.existsByNomeSala(body.nomeSala())) throw new BadRequestException("Esiste già una sala con questo nome!");

        // creazione dell'oggetto
        Sala sala = new Sala(
                body.nomeSala(),
                body.indirizzo(),
                body.capienza(),
                TipoSala.valueOf(body.tipoSala()),
                body.prezzoOrario(),
                body.imageURL(),
                body.comune(),
                body.regione(),
                body.cap(),
                body.telefono(),
                body.website()
        );

        // salvataggio del tutto
        this.salaRepository.save(sala);

        // return del response
        return new NewSalaRespDTO(sala.getId());

    }

    public Page<Sala> findAllSale(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.salaRepository.findAll(pageable);
    }

    public void findAndDeleteSala(UUID idSala){
        Sala sala = this.findById(String.valueOf(idSala));
        this.salaRepository.delete(sala);
    }

    public Sala findSala(String nomeSala) {
        Sala sala = this.salaRepository.findByNomeSala(nomeSala).orElseThrow(() -> new NotFoundException("La sala di nome " + nomeSala + " non è stata trovata"));
        return sala;
    }

    public Sala findById (String id) {
        return this.salaRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Sala non trovata con id: " + id));
    }

    // metodo per associare il proprietario alla sala
    public void associaProprietario(UUID salaId, @AuthenticationPrincipal Utente proprietario) {
        Sala sala = this.findById(String.valueOf(salaId));
        sala.setProprietario(proprietario);
        this.salaRepository.save(sala);
    }


    public Sala findAndUpdate(UUID idSala, NewSalaDTO body) {
        Sala salaFound = this.findById(String.valueOf(idSala));
        if (salaFound == null) throw new EntityNotFoundException("Sala con ID " + idSala + " non trovata.");

        // Aggiornamento dei campi non nulli
        Optional.ofNullable(body.nomeSala()).ifPresent(salaFound::setNomeSala);
        Optional.ofNullable(body.regione()).ifPresent(salaFound::setRegione);
        Optional.ofNullable(body.comune()).ifPresent(salaFound::setComune);
        Optional.ofNullable(body.cap()).ifPresent(salaFound::setCap);
        Optional.ofNullable(body.indirizzo()).ifPresent(salaFound::setIndirizzo);
        Optional.ofNullable(body.capienza()).ifPresent(salaFound::setCapienza);
        Optional.ofNullable(body.prezzoOrario()).ifPresent(salaFound::setPrezzoOrario);
        Optional.ofNullable(body.imageURL()).ifPresent(salaFound::setImageURL);
        Optional.ofNullable(body.telefono()).ifPresent(salaFound::setTelefono);
        Optional.ofNullable(body.website()).ifPresent(salaFound::setWebsite);

        return salaRepository.save(salaFound);
    }


}
