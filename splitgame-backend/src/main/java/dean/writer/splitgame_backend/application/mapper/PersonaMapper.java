package dean.writer.splitgame_backend.application.mapper;

import dean.writer.splitgame_backend.application.rest.dto.PersonaResponse;
import dean.writer.splitgame_backend.domain.model.Persona;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper: Convierte entre Persona (dominio) y PersonaResponse (DTO)
 */
@Component
public class PersonaMapper {

    public PersonaResponse toResponse(Persona persona) {
        return PersonaResponse.builder()
                .id(persona.getId())
                .nombre(persona.getNombre())
                .fechaCreacion(persona.getFechaCreacion())
                .build();
    }

    public List<PersonaResponse> toResponseList(List<Persona> personas) {
        return personas.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
