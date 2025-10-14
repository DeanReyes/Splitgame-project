package dean.writer.splitgame_backend.infrastructure.persistence.repository;

import dean.writer.splitgame_backend.infrastructure.persistence.entity.HistorialJuegoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para Historial de Juegos
 */
@Repository
public interface JpaHistorialJuegoRepository extends JpaRepository<HistorialJuegoEntity, Long> {

    @Query("SELECT h FROM HistorialJuegoEntity h ORDER BY h.fechaJuego DESC")
    List<HistorialJuegoEntity> findTopByOrderByFechaJuegoDesc(@Param("limite") int limite);

    List<HistorialJuegoEntity> findByGanadorIdOrderByFechaJuegoDesc(Long ganadorId);
}
