package dean.writer.splitgame_backend.application.rest.controller;

import dean.writer.splitgame_backend.application.mapper.PersonaMapper;
import dean.writer.splitgame_backend.application.rest.dto.CrearPersonaRequest;
import dean.writer.splitgame_backend.application.rest.dto.PersonaResponse;
import dean.writer.splitgame_backend.domain.model.Persona;
import dean.writer.splitgame_backend.domain.port.in.GestionarPersonaUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST: Gestión de Personas
 * Endpoints para crear, listar y eliminar personas
 */
@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PersonaController {

    private final GestionarPersonaUseCase gestionarPersonaUseCase;
    private final PersonaMapper personaMapper;

    @PostMapping
    public ResponseEntity<PersonaResponse> crear(@Valid @RequestBody CrearPersonaRequest request) {
        Persona persona = gestionarPersonaUseCase.crear(request.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personaMapper.toResponse(persona));
    }

    @GetMapping
    public ResponseEntity<List<PersonaResponse>> obtenerTodas() {
        List<Persona> personas = gestionarPersonaUseCase.obtenerTodas();
        return ResponseEntity.ok(personaMapper.toResponseList(personas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonaResponse> obtenerPorId(@PathVariable Long id) {
        return gestionarPersonaUseCase.obtenerPorId(id)
                .map(persona -> ResponseEntity.ok(personaMapper.toResponse(persona)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        gestionarPersonaUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
