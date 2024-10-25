package LuQ.Music_hall.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@JsonIgnoreProperties("listaUtenti")
public class Ruolo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;


    private String nomeRuolo;

    public Ruolo() {
    }

    public Ruolo(String nomeRuolo) {
        this.nomeRuolo = nomeRuolo;
    }

    // lista di utenti del ruolo
    @ManyToMany(mappedBy = "listaRuoli")
    private Set<Utente> listaUtenti = new HashSet<>();

    // metodo per aggiungere un utente al ruolo
    public void addUsertoRole(Utente utente) {
        listaUtenti.add(utente);
    }

    @Override
    public String toString() {
        return "Ruolo{" +
                "\nid=" + id +
                ",\n nomeRuolo='" + nomeRuolo + '\'' +
                //", listaUtenti=" + listaUtenti +
                '}';
    }
}
