package LuQ.Music_hall.repositories;

import LuQ.Music_hall.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Utente> findByEmail(String email);
}
