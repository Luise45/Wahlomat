/**
 * 
 * @author ??
 * 
 */

package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.repository.KandidatenRepository;
import htw.gruppe.backend.record.AuthResponse;
import htw.gruppe.backend.record.LoginRequest;
import htw.gruppe.backend.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service für die Authentifizierung (Login).
 * Prüft die Zugangsdaten eines Kandidaten und erstellt ein JWT.
 *
 * @author Karsli
 * @author Tabatt
 * @author Bektas
 * @author Schmidt
 * @author Erdogan
 */
@Service
@Transactional
public class AuthService {

    private final KandidatenRepository userRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Konstruktor für den AuthService.
     *
     * @param userRepository Repository zum Laden der Kandidaten
     * @param jwtUtil Hilfsklasse zum Erstellen von JWTs
     */
    public AuthService(KandidatenRepository userRepository,  JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder =new BCryptPasswordEncoder();
    }


    /**
     * Führt den Login eines Kandidaten durch.
     * Prüft Matrikelnummer und Passwort und gibt bei Erfolg ein JWT zurück.
     *
     * @param request Login-Daten (Matrikelnummer und Passwort)
     * @return AuthResponse mit JWT und Rückmeldung
     * @throws RuntimeException wenn die Anmeldedaten ungültig sind
     */
    public AuthResponse login (LoginRequest request){
        // User finden
        Kandidat user = userRepository.findByMatrikelnummer(request.matrikelnummer())
                .orElseThrow(() -> new RuntimeException("Ungültige Anmeldedaten"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Falsches Passwort");
        }

     // JWT mit Benutzerkennung (Matrikelnummer/Username) und Rolle erzeugen
      String token = jwtUtil.generateToken(user.getMatrikelnummer(), user.getRole()); 


        // Response erstellen (Record-Syntax)
        return new AuthResponse(
                token,
                user.getMatrikelnummer(),
                "Login erfolgreich"
        );

    }


}
