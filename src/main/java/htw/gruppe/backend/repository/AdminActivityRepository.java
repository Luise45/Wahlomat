package htw.gruppe.backend.repository;

import htw.gruppe.backend.entity.AdminActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository für Admin-Aktivitäten.
 * Ermöglicht das Speichern und Laden des Aktivitätsverlaufs.
 */
public interface AdminActivityRepository extends JpaRepository<AdminActivity, Long> {

    /**
     * Liefert alle Aktivitäten sortiert nach Zeitpunkt (neueste zuerst).
     */
    List<AdminActivity> findAllByOrderByCreatedAtDesc();
}
