package htw.gruppe.backend.repository;

import htw.gruppe.backend.entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository für Registrierungs-Token.
 *
 * Dieses Repository stellt CRUD-Operationen für {@link RegistrationToken}-Entitäten bereit
 * und erweitert die Standardmethoden von {@link JpaRepository}.
 * Zusätzlich wird eine Methode bereitgestellt, um ein Token anhand seines Strings zu suchen.
 *
 *
 * @author Schmidt
 * @version 1.0
 */

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {

    /**
     * Sucht einen RegistrationToken anhand des Token-Strings.
     *
     * @param token der eindeutige Token-String
     * @return Optional mit {@link RegistrationToken}, falls vorhanden
     */
    Optional<RegistrationToken> findByToken(String token);
}
