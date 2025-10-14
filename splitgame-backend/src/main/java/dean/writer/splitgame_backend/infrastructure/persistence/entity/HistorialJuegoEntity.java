package dean.writer.splitgame_backend.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA: Historial de Juegos
 * Representa la tabla 'historial_juegos' en PostgreSQL
 */
@Entity
@Table(name = "historial_juegos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistorialJuegoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ganador_id", nullable = false)
    private Integer ganadorId;

    @Column(name = "fecha_juego")
    private LocalDateTime fechaJuego;

    @Column(name = "total_participantes", nullable = false)
    private Integer totalParticipantes;

    @OneToMany(mappedBy = "juego", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ParticipanteJuegoEntity> participantes = new ArrayList<>();

    // Método helper para agregar participantes
    public void agregarParticipante(ParticipanteJuegoEntity participante) {
        participantes.add(participante);
        participante.setJuego(this);
    }
}
