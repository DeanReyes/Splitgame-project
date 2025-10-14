package dean.writer.splitgame_backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad de dominio: Juego
 * Representa un juego jugado donde se eligió quién paga
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Juego {

    private Long id;
    private Long ganadorId;
    private LocalDateTime fechaJuego;
    private Integer totalParticipantes;
    private List<Long> participantesIds;

    // Métodos de negocio

    public void validar() {
        if (ganadorId == null) {
            throw new IllegalArgumentException("Debe haber un ganador (quien paga)");
        }

        if (participantesIds == null || participantesIds.isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos un participante");
        }

        if (participantesIds.size() < 2) {
            throw new IllegalArgumentException("Se necesitan al menos 2 participantes para jugar");
        }

        if (!participantesIds.contains(ganadorId)) {
            throw new IllegalArgumentException("El ganador debe estar en la lista de participantes");
        }

        if (totalParticipantes == null || totalParticipantes != participantesIds.size()) {
            throw new IllegalArgumentException("El total de participantes no coincide");
        }
    }

    public boolean esValido() {
        return ganadorId != null
                && participantesIds != null
                && participantesIds.size() >= 2
                && participantesIds.contains(ganadorId)
                && totalParticipantes != null
                && totalParticipantes == participantesIds.size();
    }

    public boolean participaPersona(Long personaId) {
        return participantesIds != null && participantesIds.contains(personaId);
    }
}
