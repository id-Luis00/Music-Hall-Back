package LuQ.Music_hall.entities;


import LuQ.Music_hall.enums.TipoSala;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;

    // tutti gli attributi che verranno passati tramite csv
    private String nomeSala;
    private String regione;
    private String comune;
    private String cap;
    private String indirizzo;
    private int capienza;
    @Enumerated(EnumType.STRING)
    private TipoSala tipoSala;
    private double prezzoOrario;
    private String imageURL;
    private String telefono;
    private String website;

    // relazione tra sala e proprietari: ogni sala ha un proprietario associato
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "proprietario_id", nullable = true) // Il proprietario è opzionale. Almeno all'inizio per poter caricare tutte le sale da subito
    private Utente proprietario;

    // lista degli utenti che hanno salvato come preferita la sala
    @ManyToMany(mappedBy = "listaSalePreferite")
    @JsonIgnore
    private Set<Utente> listaUtenti = new HashSet<>();

    public Sala() {
    }

    // costruttore completo per inserire tutti i dati necessari se in possesso
    public Sala(String nomeSala,
                String indirizzo,
                int capienza,
                TipoSala tipoSala,
                double prezzoOrario,
                String imageURL,
                Utente proprietario,
                String comune,
                String regione,
                String cap,
                String telefono,
                String website) {
        this.nomeSala = nomeSala;
        this.indirizzo = indirizzo;
        this.capienza = capienza;
        this.tipoSala = tipoSala;
        this.prezzoOrario = prezzoOrario;
        this.imageURL = imageURL;
        this.proprietario = proprietario;
        this.comune = comune;
        this.regione = regione;
        this.cap = cap;
        this.telefono = telefono;
        this.website = website;
    }

    // costruttore senza il proprietario per inserire le sale senza proprietario (probabilmente la userò solo all'inizio)
    public Sala(String nomeSala,
                String indirizzo,
                int capienza,
                TipoSala tipoSala,
                double prezzoOrario,
                String imageURL,
                String comune,
                String regione,
                String cap,
                String telefono,
                String website) {
        this.nomeSala = nomeSala;
        this.indirizzo = indirizzo;
        this.capienza = capienza;
        this.tipoSala = tipoSala;
        this.prezzoOrario = prezzoOrario;
        this.imageURL = imageURL;
        this.comune = comune;
        this.regione = regione;
        this.cap = cap;
        this.telefono = telefono;
        this.website = website;
    }

    // Costruttore senza proprietario e immagine, per rendere opzionale l'img se non la si ha. In questo caso verrà inserita una immagine default
    public Sala(String nomeSala,
                String indirizzo,
                int capienza,
                TipoSala tipoSala,
                double prezzoOrario,
                String comune,
                String regione,
                String cap,
                String telefono,
                String website) {
        this.nomeSala = nomeSala;
        this.indirizzo = indirizzo;
        this.capienza = capienza;
        this.tipoSala = tipoSala;
        this.prezzoOrario = prezzoOrario;
        this.comune = comune;
        this.regione = regione;
        this.cap = cap;
        this.telefono = telefono;
        this.website = website;
    }


}
