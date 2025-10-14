package dean.writer.splitgame_backend.application.mapper;

import dean.writer.splitgame_backend.application.rest.dto.JuegoResponse;
import dean.writer.splitgame_backend.domain.model.Juego;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper: Convierte entre Juego (dominio) y JuegoResponse (DTO)
 */
@Component
public class JuegoMapper {

    public JuegoResponse toResponse(Juego juego) {
        return JuegoResponse.builder()
                .id(juego.getId())
                .ganadorId(juego.getGanadorId())
                .fechaJuego(juego.getFechaJuego())
                .totalParticipantes(juego.getTotalParticipantes())
                .participantesIds(juego.getParticipantesIds())
                .build();
    }

    public List<JuegoResponse> toResponseList(List<Juego> juegos) {
        return juegos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
