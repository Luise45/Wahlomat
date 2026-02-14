/**
 * Entität für Registrierungs-Token.
 *
 * Ein RegistrationToken wird verwendet, um die Registrierung eines Kandidaten
 * über einen zeitlich begrenzten Token zu validieren.
 * Es enthält die zugehörige Matrikelnummer, den Token selbst und das Ablaufdatum.
 *
 * @author Schmidt
 * @version 1.0
 */

package htw.gruppe.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Repräsentiert ein Token, das für die Registrierung eines Kandidaten verwendet wird.
 *
 * Jedes Token ist einem Kandidaten über die Matrikelnummer zugeordnet
 * und hat ein Ablaufdatum, nach dem es nicht mehr gültig ist.
 *
 */
@Entity
public class RegistrationToken {

    /** Eindeutige ID des Tokens. Wird automatisch generiert. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Der eindeutige Token-Wert als String. */
    private String token;

    /** Zeitpunkt, zu dem das Token abläuft und nicht mehr gültig ist. */
    private LocalDateTime expiresAt;

    /** Die Matrikelnummer des Kandidaten, dem dieses Token zugeordnet ist. */
    private String matrikelnummer;


    /**
     * Gibt die eindeutige ID des Tokens zurück.
     * @return die ID des Tokens
     */
    public Long getId() {
        return id;
    }

    /**
     * Setzt die eindeutige ID des Tokens.
     * @param id die neue ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gibt den Token-Wert zurück.
     * @return der Token-String
     */
    public String getToken() {
        return token;
    }

    /**
     * Setzt den Token-Wert.
     * @param token der neue Token-String
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gibt den Zeitpunkt zurück, zu dem das Token abläuft.
     * @return das Ablaufdatum und die Uhrzeit
     */
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    /**
     * Setzt das Ablaufdatum des Tokens.
     * @param expiresAt neues Ablaufdatum und Uhrzeit
     */
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    /**
     * Gibt die Matrikelnummer des Kandidaten zurück, dem das Token zugeordnet ist.
     * @return die Matrikelnummer
     */
    public String getMatrikelnummer() {
        return matrikelnummer;
    }

    /**
     * Setzt die Matrikelnummer des Kandidaten, dem das Token zugeordnet ist.
     * @param matrikelnummer die neue Matrikelnummer
     */
    public void setMatrikelnummer(String matrikelnummer) {
        this.matrikelnummer = matrikelnummer;
    }
}
