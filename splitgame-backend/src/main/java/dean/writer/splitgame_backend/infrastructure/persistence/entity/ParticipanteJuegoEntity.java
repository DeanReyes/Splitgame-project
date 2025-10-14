package dean.writer.splitgame_backend.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad JPA: Participante de Juego
 * Representa la tabla 'participantes_juego' en PostgreSQL
 */
@Entity
@Table(name = "participantes_juego")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipanteJuegoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "juego_id", nullable = false)
    private HistorialJuegoEntity juego;

    @Column(name = "persona_id", nullable = false)
    private Integer personaId;
}
