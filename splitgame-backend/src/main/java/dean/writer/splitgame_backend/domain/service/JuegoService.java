package dean.writer.splitgame_backend.domain.service;

import dean.writer.splitgame_backend.domain.model.Juego;
import dean.writer.splitgame_backend.domain.model.Persona;
import dean.writer.splitgame_backend.domain.port.in.RegistrarJuegoUseCase;
import dean.writer.splitgame_backend.domain.port.out.JuegoRepository;
import dean.writer.splitgame_backend.domain.port.out.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * Servicio de dominio: Gestión de Juegos
 * Implementa la lógica de negocio para registrar juegos
 */
@Service
@RequiredArgsConstructor
@Transactional
public class JuegoService implements RegistrarJuegoUseCase {

    private final JuegoRepository juegoRepository;
    private final PersonaRepository personaRepository;
    private final Random random = new Random();

    @Override
    public Juego registrarConGanadorAleatorio(List<Long> participantesIds) {
        // Validar que todas las personas existen
        validarParticipantes(participantesIds);

        // Elegir ganador aleatorio
        Long ganadorId = elegirGanadorAleatorio(participantesIds);

        // Crear el juego
        Juego juego = Juego.builder()
                .ganadorId(ganadorId)
                .participantesIds(participantesIds)
                .totalParticipantes(participantesIds.size())
                .fechaJuego(LocalDateTime.now())
                .build();

        // Validar el juego
        juego.validar();

        // Guardar y retornar
        return juegoRepository.guardar(juego);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Juego> obtenerUltimos(int limite) {
        if (limite <= 0) {
            throw new IllegalArgumentException("El límite debe ser mayor a 0");
        }
        return juegoRepository.buscarUltimosJuegos(limite);
    }

    private Long elegirGanadorAleatorio(List<Long> participantesIds) {
        if (participantesIds == null || participantesIds.isEmpty()) {
            throw new IllegalArgumentException("No hay participantes para elegir");
        }
        int indiceAleatorio = random.nextInt(participantesIds.size());
        return participantesIds.get(indiceAleatorio);
    }

    private void validarParticipantes(List<Long> participantesIds) {
        List<Persona> personasEncontradas = personaRepository.buscarPorIds(participantesIds);

        if (personasEncontradas.size() != participantesIds.size()) {
            throw new IllegalArgumentException("Algunos participantes no existen en la base de datos");
        }
    }
}
