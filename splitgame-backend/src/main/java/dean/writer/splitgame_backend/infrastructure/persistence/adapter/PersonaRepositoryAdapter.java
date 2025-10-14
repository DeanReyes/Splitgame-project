package dean.writer.splitgame_backend.infrastructure.persistence.adapter;

import dean.writer.splitgame_backend.domain.model.Persona;
import dean.writer.splitgame_backend.domain.port.out.PersonaRepository;
import dean.writer.splitgame_backend.infrastructure.persistence.entity.PersonaEntity;
import dean.writer.splitgame_backend.infrastructure.persistence.repository.JpaPersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador: Implementa PersonaRepository usando JPA
 * Convierte entre modelo de dominio y entidades JPA
 */
@Component
@RequiredArgsConstructor
public class PersonaRepositoryAdapter implements PersonaRepository {

    private final JpaPersonaRepository jpaPersonaRepository;

    @Override
    public Persona guardar(Persona persona) {
        PersonaEntity entity = toEntity(persona);
        PersonaEntity savedEntity = jpaPersonaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Persona> buscarPorId(Long id) {
        return jpaPersonaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Persona> buscarTodas() {
        return jpaPersonaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long id) {
        jpaPersonaRepository.deleteById(id);
    }

    @Override
    public boolean existe(Long id) {
        return jpaPersonaRepository.existsById(id);
    }

    @Override
    public List<Persona> buscarPorIds(List<Long> ids) {
        return jpaPersonaRepository.findByIdIn(ids).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    // Mappers: Entity <-> Domain

    private PersonaEntity toEntity(Persona persona) {
        return PersonaEntity.builder()
                .id(persona.getId() != null ? persona.getId().intValue() : null)
                .nombre(persona.getNombre())
                .fechaCreacion(persona.getFechaCreacion())
                .build();
    }

    private Persona toDomain(PersonaEntity entity) {
        return Persona.builder()
                .id(entity.getId() != null ? entity.getId().longValue() : null)
                .nombre(entity.getNombre())
                .fechaCreacion(entity.getFechaCreacion())
                .build();
    }
}
