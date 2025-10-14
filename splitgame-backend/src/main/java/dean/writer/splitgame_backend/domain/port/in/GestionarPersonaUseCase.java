package dean.writer.splitgame_backend.domain.port.in;

import dean.writer.splitgame_backend.domain.model.Persona;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada: Gestionar personas
 * Define el contrato para operaciones con personas
 */
public interface GestionarPersonaUseCase {

    /**
     * Crea una nueva persona
     * @param nombre Nombre de la persona
     * @return La persona creada
     */
    Persona crear(String nombre);

    /**
     * Obtiene todas las personas
     * @return Lista de todas las personas
     */
    List<Persona> obtenerTodas();

    /**
     * Obtiene una persona por ID
     * @param id ID de la persona
     * @return Optional con la persona si existe
     */
    Optional<Persona> obtenerPorId(Long id);

    /**
     * Elimina una persona
     * @param id ID de la persona a eliminar
     */
    void eliminar(Long id);
}
