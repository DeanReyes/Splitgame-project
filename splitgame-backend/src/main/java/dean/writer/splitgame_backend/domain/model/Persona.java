package dean.writer.splitgame_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad de dominio: Persona
 * Representa a un participante del juego
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    private Long id;
    private String nombre;
    private LocalDateTime fechaCreacion;

    // Métodos de negocio

    public void validar() {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la persona no puede estar vacío");
        }

        if (nombre.length() > 100) {
            throw new IllegalArgumentException("El nombre no puede exceder 100 caracteres");
        }
    }

    public boolean esNombreValido() {
        return nombre != null && !nombre.trim().isEmpty() && nombre.length() <= 100;
    }
}
