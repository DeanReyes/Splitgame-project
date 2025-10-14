package dean.writer.splitgame_backend.application.rest.controller;

import dean.writer.splitgame_backend.application.mapper.JuegoMapper;
import dean.writer.splitgame_backend.application.rest.dto.JuegoResponse;
import dean.writer.splitgame_backend.application.rest.dto.RegistrarJuegoRequest;
import dean.writer.splitgame_backend.domain.model.Juego;
import dean.writer.splitgame_backend.domain.port.in.RegistrarJuegoUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST: Gestión de Juegos
 * Endpoints para registrar y consultar juegos
 */
@RestController
@RequestMapping("/api/juegos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class JuegoController {

    private final RegistrarJuegoUseCase registrarJuegoUseCase;
    private final JuegoMapper juegoMapper;

    @PostMapping
    public ResponseEntity<JuegoResponse> registrarJuego(@Valid @RequestBody RegistrarJuegoRequest request) {
        Juego juego = registrarJuegoUseCase.registrarConGanadorAleatorio(
                request.getParticipantesIds()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(juegoMapper.toResponse(juego));
    }

    @GetMapping("/ultimos")
    public ResponseEntity<List<JuegoResponse>> obtenerUltimos(
            @RequestParam(defaultValue = "10") int limite
    ) {
        List<Juego> juegos = registrarJuegoUseCase.obtenerUltimos(limite);
        return ResponseEntity.ok(juegoMapper.toResponseList(juegos));
    }
}
