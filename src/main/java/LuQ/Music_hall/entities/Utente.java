package LuQ.Music_hall.entities;


import LuQ.Music_hall.enums.TipoMusicista;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"listaRuoli", "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked"})
public class Utente implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String nome;
    private String cognome;
    private String username;
    private String email;
    private String password;
    private LocalDate dataDiNascita;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private TipoMusicista tipoMusicista;

    // utente deve avere una lista di sale preferite
    // TODO: creare lista di sale preferite
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "utenti_sale",
            joinColumns = @JoinColumn(name = "id_utente"),
            inverseJoinColumns = @JoinColumn(name = "id_sala")
    )
    private Set<Sala> listaSalePreferite = new HashSet<>();

    // utente deve avere la lista dei ruoli
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ruoli_utenti",
            joinColumns = @JoinColumn(name = "id_utente"),
            inverseJoinColumns = @JoinColumn(name = "id_ruolo")
    )
    private Set<Ruolo> listaRuoli = new HashSet<>();

    @OneToMany(mappedBy = "proprietario", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Sala> listaSale = new HashSet<>();


    public Utente() {
    }

    public Utente(String nome, String cognome, String username, String email, String password, LocalDate dataDiNascita, TipoMusicista tipoMusicista) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dataDiNascita = dataDiNascita;
        this.tipoMusicista = tipoMusicista;
    }

    public Utente(String nome, String cognome, String email, String password, LocalDate dataDiNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.dataDiNascita = dataDiNascita;
    }

    // metodo per aggiungere ruolo all'utente
    public void addRoleToUser(Ruolo ruolo) {
        listaRuoli.add(ruolo);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.listaRuoli.stream()
                .map(ruolo -> new SimpleGrantedAuthority(ruolo.getNomeRuolo()))
                .collect(Collectors.toSet());

    }

    @Override
    public String toString() {
        return "Utente{" +
                "\nid=" + id +
                ",\n nome='" + nome + '\'' +
                ",\n cognome='" + cognome + '\'' +
                ",\n username='" + username + '\'' +
                ",\n email='" + email + '\'' +
                ",\n password='" + password + '\'' +
                ",\n dataDiNascita=" + dataDiNascita +
                ",\n avatar='" + avatar + '\'' +
                ",\n tipoMusicista=" + tipoMusicista +
                ",\n listaRuoli=" + listaRuoli +
                '}';
    }
}
