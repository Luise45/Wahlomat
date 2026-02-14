package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.entity.RegistrationToken;
import htw.gruppe.backend.repository.KandidatenRepository;
import htw.gruppe.backend.repository.RegistrationTokenRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RegisterServiceTest {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private KandidatenRepository kandidatenRepository;

    @Autowired
    private RegistrationTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @TestConfiguration
    static class TestConfig {

        @Bean
        public JavaMailSender javaMailSender() {
            return new JavaMailSenderImpl();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    // Start registrirung. Kandidat exsistier nicht, Token wir erstellt
    @Test
    void Tokenerstellen() {

        Optional<String> tokenOpt = registerService.startRegistration("s0123456"); assertThat(tokenOpt).isPresent();

        RegistrationToken token = tokenRepository.findByToken(tokenOpt.get()).orElseThrow();

        assertThat(token.getMatrikelnummer()).isEqualTo("s0123456");
        assertThat(token.getExpiresAt()).isAfter(LocalDateTime.now());
    }

    //  Token wird korrekt behandelt
    @Test
    void Token() {

        String token = registerService.createRegistrationToken("s0654321");

        RegistrationToken saved = tokenRepository.findByToken(token).orElseThrow();

        assertThat(saved.getMatrikelnummer()).isEqualTo("s0654321");
        assertThat(saved.getExpiresAt())
            .isAfter(LocalDateTime.now())
         .isBefore(LocalDateTime.now().plusHours(2));
    }

    // Registration fertig und Token wird geloescht.
    @Test
    void fertigRegistration() {

        RegistrationToken token = new RegistrationToken();
        token.setToken("valid-token");
        token.setMatrikelnummer("s0999999");
        token.setExpiresAt(LocalDateTime.now().plusMinutes(30));

        tokenRepository.save(token);

        boolean result =
                registerService.completeRegistration("valid-token", "Anna", "Schmidt", "secret");


        assertThat(result).isTrue();

        Kandidat kandidat = kandidatenRepository.findByMatrikelnummer("s0999999").orElseThrow();

        assertThat(kandidat.getVorname()).isEqualTo("Anna");
        assertThat(kandidat.getNachname()).isEqualTo("Schmidt");
        assertThat(kandidat.getFachbereich()).isEqualTo("noch nicht gewählt");
        assertThat(passwordEncoder.matches("secret", kandidat.getPassword())).isTrue();
        assertThat(tokenRepository.findByToken("valid-token")).isEmpty();
    }

    // Der Token exsistiert nicht
    @Test
    void KeinToken() {
        boolean result =
                registerService.completeRegistration("does not exist", "John", "Doe", "pw" );
        assertThat(result).isFalse();
    }

    // Den Kandidaten giebt es schon
    @Test
    void KandidatgiebtEsSchon() {

        Kandidat kandidat = new Kandidat();
        kandidat.setMatrikelnummer("s0777777");
        kandidat.setVorname("Existing");
        kandidat.setNachname("User");
        kandidatenRepository.save(kandidat);

        RegistrationToken token = new RegistrationToken();
        token.setToken("token");
        token.setMatrikelnummer("s0777777");
        token.setExpiresAt(LocalDateTime.now().plusMinutes(10));  tokenRepository.save(token);

        boolean result = registerService.completeRegistration("token", "New", "User", "pw");
        assertThat(result).isFalse();
        assertThat(tokenRepository.findByToken("token")).isPresent();
    }
}

