package htw.gruppe.backend.repository;

import java.util.List;
import htw.gruppe.backend.entity.Kandidat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository für die Verwaltung von Kandidaten.
 *
 * Dieses Repository stellt CRUD-Operationen für {@link Kandidat}-Entitäten bereit
 * und erweitert die Standardmethoden von {@link JpaRepository}.
 * Zusätzlich werden Methoden bereitgestellt, um Kandidaten anhand der Matrikelnummer
 * zu suchen und auf deren Existenz zu prüfen.
 *
 *
 * @author Karsli
 * @author Schmidt
 * @version 1.0
 */
public interface KandidatenRepository extends JpaRepository<Kandidat, Long> {

    /**
     * Sucht einen Kandidaten anhand der Matrikelnummer.
     *
     * @param matrikelnummer die eindeutige Matrikelnummer des Kandidaten
     * @return Optional mit {@link Kandidat}, falls vorhanden
     */
    Optional<Kandidat> findByMatrikelnummer(String matrikelnummer);

    /**
     * Prüft, ob ein Kandidat mit der angegebenen Matrikelnummer bereits existiert.
     *
     * @param matrikelnummer die Matrikelnummer
     * @return true, falls ein Kandidat existiert, sonst false
     */
    boolean existsByMatrikelnummer(String matrikelnummer);

  List<Kandidat> findAllByRole(String role);

}