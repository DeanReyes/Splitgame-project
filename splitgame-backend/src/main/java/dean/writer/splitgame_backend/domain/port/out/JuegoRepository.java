package dean.writer.splitgame_backend.domain.port.out;

import dean.writer.splitgame_backend.domain.model.Juego;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Repositorio de Juegos
 * Define el contrato para persistencia de juegos
 */
public interface JuegoRepository {

    Juego guardar(Juego juego);

    Optional<Juego> buscarPorId(Long id);

    List<Juego> buscarTodos();

    List<Juego> buscarUltimosJuegos(int limite);

    List<Juego> buscarPorGanador(Long ganadorId);
}
