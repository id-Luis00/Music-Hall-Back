package LuQ.Music_hall.repositories;

import LuQ.Music_hall.entities.Sala;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SalaRepository extends JpaRepository<Sala, UUID>, JpaSpecificationExecutor<Sala> {
    boolean existsByNomeSala(String nomeSala);
    Optional<Sala> findByNomeSala(String nomeSala);
}
