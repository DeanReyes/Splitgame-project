package dean.writer.splitgame_backend.application.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO Response: Juego
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JuegoResponse {

    private Long id;
    private Long ganadorId;
    private LocalDateTime fechaJuego;
    private Integer totalParticipantes;
    private List<Long> participantesIds;
}
