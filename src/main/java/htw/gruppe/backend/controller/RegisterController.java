
/**
 * Controller für die Benutzerregistrierung.
 *
 * Dieser Controller stellt Endpunkte bereit, um die Registrierung eines Kandidaten
 * zu starten und abzuschließen. Dabei wird ein zeitlich begrenzter Token erstellt,
 * der per E-Mail verschickt wird, um die Registrierung zu bestätigen.
 *
 * Endpunkte:
 *
 *     POST /api/start-registration: Startet die Registrierung und versendet einen Token-Link.
 *     POST /api/complete-registration: Schließt die Registrierung mit Token und Benutzerdaten ab.
 *
 *
 * Cross-Origin Requests von http://localhost:4200 werden erlaubt.
 *
 * @author Schmidt
 * @version 1.0
 */
package htw.gruppe.backend.controller;

import htw.gruppe.backend.record.RegisterRequest;
import htw.gruppe.backend.record.CompleteRegistrationRequest;
import htw.gruppe.backend.service.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controller fuer Register Endpunkte
 * @author Schmidt
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }


    /**
     * Startet die Registrierung eines Kandidaten.
     *
     * Es wird ein zeitlich begrenzter Token erzeugt und per E-Mail an
     * die HTW-Mailadresse des Kandidaten gesendet.
     *
     * @param request RegisterRequest mit der Matrikelnummer
     * @return ResponseEntity mit Status 200 und Erfolgsnachricht,
     *         oder 400 falls die Matrikelnummer bereits registriert ist
     */
    @PostMapping("/start-registration")
    public ResponseEntity<Map<String, String>> startRegistration(
            //swagger
            @org.springframework.web.bind.annotation.RequestBody RegisterRequest request) {

        Map<String, String> response = new HashMap<>();

        Optional<String> tokenOpt = registerService.startRegistration(request.matrikelnummer());

        if (tokenOpt.isEmpty()) {
            response.put("message", "Diese Matrikelnummer ist bereits registriert.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        String token = tokenOpt.get();

        String subject = "Registrierung abschließen";
        String text =
                "Bitte klicke auf folgenden Link, um deine Registrierung abzuschließen:\n"
                        + "http://localhost:4200/kandidat-registrierung?token=" + token;



        registerService.sendEmail(request.matrikelnummer() + "@htw-berlin.de", subject, text);

        response.put("message", "Registrierungslink wurde an "
                + request.matrikelnummer() + "@htw-berlin.de versendet!");

        return ResponseEntity.ok(response);
    }


    /**
     * Schließt die Registrierung eines Kandidaten ab.
     *
     * Überprüft den Token und speichert die Benutzerdaten.
     *
     * @param request CompleteRegistrationRequest mit Token, Vorname, Nachname und Passwort
     * @return ResponseEntity mit Status 200 und Erfolgsnachricht,
     *         oder 400 falls der Token ungültig oder abgelaufen ist
     */
    @PostMapping("/complete-registration")
    public ResponseEntity<Map<String, String>> completeRegistration(
            //swagger
            @org.springframework.web.bind.annotation.RequestBody CompleteRegistrationRequest request) {

        Map<String, String> response = new HashMap<>();

        boolean success = registerService.completeRegistration(
                request.token(),
                request.vorname(),
                request.nachname(),
                request.password()
        );

        if (!success) {
            response.put("message", "Token ungültig, abgelaufen oder Matrikelnummer bereits registriert.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response.put("message", "Registrierung erfolgreich!");
        response.put("redirectUrl", "/login");
        return ResponseEntity.ok(response);
    }
}
