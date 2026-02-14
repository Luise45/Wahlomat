package htw.gruppe.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import htw.gruppe.backend.entity.KandidatAufWahlliste;
import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.entity.Wahlliste;

/**
 * Repository für den Zugriff auf die Zuordnung zwischen Kandidaten und Wahllisten.
 * <p>
 * Diese Schnittstelle stellt Methoden bereit, um:
 * <ul>
 *   <li>Zuordnungen zwischen Kandidaten und Wahllisten zu prüfen</li>
 *   <li>die Anzahl von Zuordnungen zu zählen</li>
 *   <li>Kandidaten von Wahllisten zu entfernen</li>
 *   <li>Zuordnungen gezielt abzufragen</li>
 * </ul>
 *
 * Die Methoden basieren auf Spring Data JPA Query-Methoden und werden automatisch
 * zur Laufzeit implementiert.
 *
 * @author Erdogan
 */

public interface KandidatAufWahllisteRepository
        extends JpaRepository<KandidatAufWahlliste, Long> {

    /**
     * Prüft, ob ein bestimmter Kandidat bereits einer bestimmten Wahlliste zugeordnet ist.
     *
     * @param kandidat der Kandidat
     * @param wahlliste die Wahlliste
     * @return {@code true}, wenn der Kandidat bereits auf der Wahlliste steht, sonst {@code false}
     */

    boolean existsByKandidatAndWahlliste(
            Kandidat kandidatId,
            Wahlliste wahllisteId);

    /**
     * Prüft, ob ein Kandidat in einem bestimmten Gremium bereits
     * auf einer Wahlliste steht.
     * <p>
     * Ein Kandidat darf pro Gremium nur auf einer Wahlliste stehen.
     *
     * @param kandidatId ID des Kandidaten
     * @param gremiumId ID des Gremiums
     * @return {@code true}, wenn der Kandidat bereits im Gremium einer Wahlliste zugeordnet ist
     */

    boolean existsByKandidatIdAndWahllisteGremiumId(
            Long kandidatId,
            Long gremiumId);

    /**
     * Zählt, auf wie vielen Wahllisten ein Kandidat insgesamt steht.
     *
     * @param kandidatId ID des Kandidaten
     * @return Anzahl der Wahllisten, auf denen der Kandidat steht
     */

    long countByKandidatId(Long kandidatId);
    
    /**
     * Zählt, wie viele Kandidaten einer bestimmten Wahlliste zugeordnet sind.
     *
     * @param wahllisteId ID der Wahlliste
     * @return Anzahl der Kandidaten auf der Wahlliste
     */

    long countByWahllisteId(Long wahllisteId);

    /**
     * Entfernt einen bestimmten Kandidaten von einer bestimmten Wahlliste.
     *
     * @param kandidatId ID des Kandidaten
     * @param wahllisteId ID der Wahlliste
     */

    void deleteByKandidatIdAndWahllisteId(
            Long kandidatId,
            Long wahllisteId);

    /**
     * Entfernt alle Kandidaten-Zuordnungen zu einer bestimmten Wahlliste.
     * <p>
     * Wird z. B. verwendet, wenn eine Wahlliste gelöscht wird.
     *
     * @param wahllisteId ID der Wahlliste
     */

    void deleteByWahllisteId(Long wahllisteId);

    /**
     * Liefert alle Kandidaten-Wahllisten-Zuordnungen für eine Liste von Kandidaten-IDs.
     *
     * @param kandidatIds Liste von Kandidaten-IDs
     * @return Liste der passenden Zuordnungen
     */

    List<KandidatAufWahlliste> findByKandidatIdIn(List<Long> kandidatIds);

}