package dean.writer.splitgame_backend.infrastructure.persistence.adapter;

import dean.writer.splitgame_backend.domain.model.Juego;
import dean.writer.splitgame_backend.domain.port.out.JuegoRepository;
import dean.writer.splitgame_backend.infrastructure.persistence.entity.HistorialJuegoEntity;
import dean.writer.splitgame_backend.infrastructure.persistence.entity.ParticipanteJuegoEntity;
import dean.writer.splitgame_backend.infrastructure.persistence.repository.JpaHistorialJuegoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador: Implementa JuegoRepository usando JPA
 * Convierte entre modelo de dominio y entidades JPA
 */
@Component
@RequiredArgsConstructor
public class JuegoRepositoryAdapter implements JuegoRepository {

    private final JpaHistorialJuegoRepository jpaHistorialJuegoRepository;

    @Override
    public Juego guardar(Juego juego) {
        HistorialJuegoEntity entity = toEntity(juego);
        HistorialJuegoEntity savedEntity = jpaHistorialJuegoRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Juego> buscarPorId(Long id) {
        return jpaHistorialJuegoRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Juego> buscarTodos() {
        return jpaHistorialJuegoRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Juego> buscarUltimosJuegos(int limite) {
        return jpaHistorialJuegoRepository.findTopByOrderByFechaJuegoDesc(limite).stream()
                .limit(limite)
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Juego> buscarPorGanador(Long ganadorId) {
        return jpaHistorialJuegoRepository.findByGanadorIdOrderByFechaJuegoDesc(ganadorId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    // Mappers: Entity <-> Domain

    private HistorialJuegoEntity toEntity(Juego juego) {
        HistorialJuegoEntity entity = HistorialJuegoEntity.builder()
                .id(juego.getId() != null ? juego.getId().intValue() : null)
                .ganadorId(juego.getGanadorId() != null ? juego.getGanadorId().intValue() : null)
                .fechaJuego(juego.getFechaJuego())
                .totalParticipantes(juego.getTotalParticipantes())
                .build();

        // Agregar participantes
        if (juego.getParticipantesIds() != null) {
            for (Long personaId : juego.getParticipantesIds()) {
                ParticipanteJuegoEntity participante = ParticipanteJuegoEntity.builder()
                        .personaId(personaId.intValue())
                        .build();
                entity.agregarParticipante(participante);
            }
        }

        return entity;
    }

    private Juego toDomain(HistorialJuegoEntity entity) {
        List<Long> participantesIds = entity.getParticipantes().stream()
                .map(p -> p.getPersonaId().longValue())
                .collect(Collectors.toList());

        return Juego.builder()
                .id(entity.getId() != null ? entity.getId().longValue() : null)
                .ganadorId(entity.getGanadorId() != null ? entity.getGanadorId().longValue() : null)
                .fechaJuego(entity.getFechaJuego())
                .totalParticipantes(entity.getTotalParticipantes())
                .participantesIds(participantesIds)
                .build();
    }
}
