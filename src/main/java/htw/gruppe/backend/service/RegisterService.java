package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.entity.RegistrationToken;
import htw.gruppe.backend.repository.KandidatenRepository;
import htw.gruppe.backend.repository.RegistrationTokenRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Service zur Verwaltung der Kandidatenregistrierung.
 *
 * Dieser Service ermöglicht das Starten und Abschließen der Registrierung,
 * inklusive Generierung eines zeitlich begrenzten Tokens und Versenden
 * von Bestätigungs-E-Mails. Außerdem wird das Passwort verschlüsselt gespeichert.
 *
 * @author Schmidt
 * @version 1.0
 */
@Service
public class RegisterService implements RegisterInterface {

    private final KandidatenRepository kandidatenRepository;
    private final RegistrationTokenRepository tokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(KandidatenRepository kandidatenRepository,
                           RegistrationTokenRepository tokenRepository,
                           JavaMailSender mailSender,
                           BCryptPasswordEncoder passwordEncoder)
    {
        this.kandidatenRepository = kandidatenRepository;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Startet die Registrierung eines Kandidaten.
     *
     * @param matrikelnummer Matrikelnummer des Kandidaten
     * @return Optional mit generiertem Token, falls Kandidat noch nicht registriert ist;
     *         ansonsten Optional.empty()
     */
    public Optional<String> startRegistration(String matrikelnummer) {
        // Prüfen, ob Kandidat schon existiert
        if (kandidatenRepository.findByMatrikelnummer(matrikelnummer).isPresent()) {
            return Optional.empty();
        }

        // Token erstellen
        String token = createRegistrationToken(matrikelnummer);
        return Optional.of(token);
    }

    /**
     * Erstellt einen neuen Registrierungs-Token für einen Kandidaten.
     *
     * @param matrikelnummer Matrikelnummer des Kandidaten
     * @return generierter Token
     */
    @Transactional
    public String createRegistrationToken(String matrikelnummer) {
        RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setToken(UUID.randomUUID().toString());
        registrationToken.setMatrikelnummer(matrikelnummer);
        registrationToken.setExpiresAt(LocalDateTime.now().plusHours(1));

        tokenRepository.save(registrationToken);
        return registrationToken.getToken();
    }


    /**
     * Schließt die Registrierung eines Kandidaten ab.
     *
     * @param token Registrierungs-Token
     * @param vorname Vorname des Kandidaten
     * @param nachname Nachname des Kandidaten
     * @param password Passwort des Kandidaten
     * @return {@code true}, wenn die Registrierung erfolgreich war; {@code false}, wenn
     *         Token ungültig, abgelaufen oder Kandidat bereits registriert ist
     */
    @Transactional
    public boolean completeRegistration(String token, String vorname, String nachname, String password) {

        Optional<RegistrationToken> tokenOpt = tokenRepository.findByToken(token);

        // Token prüfen
        if (tokenOpt.isEmpty() || tokenOpt.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            return false;
        }

        String matrikelnummer = tokenOpt.get().getMatrikelnummer();

        // Prüfen, ob Kandidat schon existiert
        if (kandidatenRepository.findByMatrikelnummer(matrikelnummer).isPresent()) {
            return false;
        }

        // Neuen Kandidaten anlegen
        Kandidat kandidat = new Kandidat();
        kandidat.setMatrikelnummer(matrikelnummer);
        kandidat.setVorname(vorname);
        kandidat.setNachname(nachname);
        kandidat.setPassword(passwordEncoder.encode(password));
        kandidat.setFachbereich("noch nicht gewählt");
        kandidat.setRole("USER");

        // Speichern und Token löschen
        kandidatenRepository.save(kandidat);
        tokenRepository.delete(tokenOpt.get());

        return true;
    }

    /**
     * Versendet eine E-Mail.
     *
     * @param to Empfängeradresse
     * @param subject Betreff der E-Mail
     * @param text Inhalt der E-Mail
     */
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("fiwproj@student.htw-berlin.de");
        mailSender.send(message);
    }
}
