package LuQ.Music_hall.repositories;

import LuQ.Music_hall.entities.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository

public interface RuoloRepository extends JpaRepository<Ruolo, UUID> {

    boolean existsByNomeRuolo(String nomeRuolo);
    Optional<Ruolo> findByNomeRuolo(String nomeRuolo);
}
