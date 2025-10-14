package dean.writer.splitgame_backend.domain.port.out;

import dean.writer.splitgame_backend.domain.model.Persona;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Repositorio de Personas
 * Define el contrato para persistencia de personas
 */
public interface PersonaRepository {

    Persona guardar(Persona persona);

    Optional<Persona> buscarPorId(Long id);

    List<Persona> buscarTodas();

    void eliminar(Long id);

    boolean existe(Long id);

    List<Persona> buscarPorIds(List<Long> ids);
}
