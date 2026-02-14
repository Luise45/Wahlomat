package htw.gruppe.backend.service;

/**
 * Interface für den Service {@link RegisterService} zur Handhabung der Registrierung.
 * <p>
 * Dieses Interface definiert die grundlegenden Operationen für die Registrierung eines Kandidaten,
 * einschließlich Token-Erstellung, Abschluss der Registrierung und Versand von E-Mails.
 * </p>
 *
 * @author Schmidt
 * @version 1.0
 */

public interface RegisterInterface {

    /**
     * Sendet eine E-Mail an die angegebene Adresse.
     *
     * @param to      Empfängeradresse
     * @param subject Betreff der E-Mail
     * @param text    Inhalt der E-Mail
     */

    void sendEmail(String to, String subject, String text);
    /**
     * Erstellt ein eindeutiges Registrierungstoken für eine Matrikelnummer.
     *
     * @param matrikelnummer Matrikelnummer des Kandidaten
     * @return das generierte Token als String
     */

    String createRegistrationToken(String matrikelnummer);

    /**
     * Schließt die Registrierung eines Kandidaten ab.
     *
     * Prüft das Token auf Gültigkeit und erstellt einen neuen Kandidateneintrag
     * mit den übergebenen Daten. Das Passwort wird verschlüsselt gespeichert.
     *
     * @param token      Registrierungstoken
     * @param vorname    Vorname des Kandidaten
     * @param nachname   Nachname des Kandidaten
     * @param password   Passwort des Kandidaten (wird verschlüsselt gespeichert)
     * @return true, wenn die Registrierung erfolgreich abgeschlossen wurde; false bei Fehler
     */
    boolean completeRegistration(String token, String vorname, String nachname, String password);
}
