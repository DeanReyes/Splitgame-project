package dean.writer.splitgame_backend.infrastructure.persistence.repository;

import dean.writer.splitgame_backend.infrastructure.persistence.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para Persona
 * Extiende JpaRepository para operaciones CRUD básicas
 */
@Repository
public interface JpaPersonaRepository extends JpaRepository<PersonaEntity, Long> {

    List<PersonaEntity> findByIdIn(List<Long> ids);
}
