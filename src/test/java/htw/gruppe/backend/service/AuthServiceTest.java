package htw.gruppe.backend.service;
import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.record.AuthResponse;
import htw.gruppe.backend.record.LoginRequest;
import htw.gruppe.backend.repository.KandidatenRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private KandidatenRepository kandidatenRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        kandidatenRepository.deleteAll();

        Kandidat user = new Kandidat();
        user.setMatrikelnummer("123456");
        user.setPassword(encoder.encode("xxxx"));
        user.setVorname("Lenna");
        user.setNachname("Berg");

        kandidatenRepository.save(user);
    }

    @Test
    void login() {
        LoginRequest request = new LoginRequest( "123456", "xxxx");
        AuthResponse response = authService.login(request);
        assertNotNull(response.token());
        assertEquals("123456", response.matrikelnummer());
        assertEquals("Login erfolgreich", response.message());
    }

    @Test
    void loginwithWrongPassword() {
        LoginRequest request = new LoginRequest("123456",  "yyyy");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.login(request));
        assertEquals("Falsches Passwort", ex.getMessage());
    }
    @Test
    void loginwithUnknownUser() {
        LoginRequest request = new LoginRequest("999999", "xxxx");

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.login(request));
        assertEquals("Ungültige Anmeldedaten", ex.getMessage());
    }
}

