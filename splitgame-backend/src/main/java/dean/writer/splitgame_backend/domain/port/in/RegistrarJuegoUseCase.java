package dean.writer.splitgame_backend.domain.port.in;

import dean.writer.splitgame_backend.domain.model.Juego;

import java.util.List;

/**
 * Puerto de entrada: Registrar un nuevo juego
 * Define el contrato para registrar quién paga la cuenta
 */
public interface RegistrarJuegoUseCase {

    /**
     * Registra un nuevo juego eligiendo aleatoriamente al ganador
     * @param participantesIds Lista de IDs de todos los participantes
     * @return El juego registrado con el ganador aleatorio
     */
    Juego registrarConGanadorAleatorio(List<Long> participantesIds);

    /**
     * Obtiene los últimos juegos jugados
     * @param limite Cantidad de juegos a obtener
     * @return Lista de juegos
     */
    List<Juego> obtenerUltimos(int limite);
}
