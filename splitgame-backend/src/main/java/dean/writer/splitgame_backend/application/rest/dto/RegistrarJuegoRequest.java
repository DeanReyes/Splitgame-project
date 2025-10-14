package dean.writer.splitgame_backend.application.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO Request: Registrar Juego
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarJuegoRequest {

    @NotEmpty(message = "Debe haber al menos un participante")
    @Size(min = 2, message = "Se necesitan al menos 2 participantes para jugar")
    private List<Long> participantesIds;
}
