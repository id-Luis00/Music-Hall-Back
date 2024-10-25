package LuQ.Music_hall.services;


import LuQ.Music_hall.entities.Ruolo;
import LuQ.Music_hall.exceptions.NotFoundException;
import LuQ.Music_hall.repositories.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuoloService {

    @Autowired
    private RuoloRepository ruoloRepository;


    // salvataggio nuovo ruolo
    public void saveRuolo(Ruolo ruolo){
        this.ruoloRepository.save(ruolo);
    }


    // controllo se esiste il nome del ruolo
    public boolean existsByNomeRuolo(String nomeRuolo){
       return this.ruoloRepository.existsByNomeRuolo(nomeRuolo);
    }

    // cerchiamo il ruolo
    public Ruolo findByNomeRuolo(String nomeRuolo) {
        return this.ruoloRepository.findByNomeRuolo(nomeRuolo).orElseThrow(() -> new NotFoundException("ruolo non trovato"));
    }
}
