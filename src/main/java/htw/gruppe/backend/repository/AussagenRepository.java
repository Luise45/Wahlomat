package htw.gruppe.backend.repository;

import htw.gruppe.backend.entity.Aussage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository für die Verwaltung von Aussagen.
 * <p>
 * Dieses Repository stellt Methoden zum Abrufen von {@link Aussage}-Entitäten bereit,
 * insbesondere zur Auswahl nur der aktiven Aussagen.
 * </p>
 *
 * @author Dumke
 * @author Schmidt
 * @author Nguemezi
 * @version 1.0
 */

public interface AussagenRepository extends JpaRepository<Aussage, Long> {

    /**
     * Findet alle Aussagen, die als aktiv markiert sind.
     *
     * @return Liste aller aktiven {@link Aussage}-Entitäten
     */
    List<Aussage> findByAktivTrue();

    /**
     * Ermittelt die Anzahl der aktiven Aussagen (aktiv = true).
     *
     * Während {@code findByAktivTrue()} eine Liste von Aussagen liefert,
     * gibt diese Methode lediglich die Gesamtanzahl zurück.
     *
     * Wird im Admin-Dashboard zur Anzeige verwendet.
     *
     * @return Anzahl aktiver Aussagen
     */
    long countByAktivTrue();
}