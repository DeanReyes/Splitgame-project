package dean.writer.splitgame_backend.application.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO Response: Persona
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaResponse {

    private Long id;
    private String nombre;
    private LocalDateTime fechaCreacion;
}
