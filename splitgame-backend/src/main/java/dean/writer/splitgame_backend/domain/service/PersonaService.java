package dean.writer.splitgame_backend.domain.service;

import dean.writer.splitgame_backend.domain.model.Persona;
import dean.writer.splitgame_backend.domain.port.in.GestionarPersonaUseCase;
import dean.writer.splitgame_backend.domain.port.out.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de dominio: Gestión de Personas
 * Implementa la lógica de negocio para personas
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PersonaService implements GestionarPersonaUseCase {

    private final PersonaRepository personaRepository;

    @Override
    public Persona crear(String nombre) {
        Persona persona = Persona.builder()
                .nombre(nombre)
                .fechaCreacion(LocalDateTime.now())
                .build();

        // Validar la persona
        persona.validar();

        // Guardar y retornar
        return personaRepository.guardar(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Persona> obtenerTodas() {
        return personaRepository.buscarTodas();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> obtenerPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return personaRepository.buscarPorId(id);
    }

    @Override
    public void eliminar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        if (!personaRepository.existe(id)) {
            throw new IllegalArgumentException("La persona con ID " + id + " no existe");
        }

        personaRepository.eliminar(id);
    }
}
