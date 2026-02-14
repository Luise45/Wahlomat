package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.ForgotPassword;
import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.repository.KandidatenRepository;
import htw.gruppe.backend.repository.ResetPasswortRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ResetServiceTest {

    @Autowired
    private ResetService resetService;

    @Autowired
    private ResetPasswortRepository resetPasswortRepository;

    @Autowired
    private KandidatenRepository kandidatenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @TestConfiguration
    static class TestConfig {

        @Bean
        public JavaMailSender javaMailSender() { return new JavaMailSenderImpl(); }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    // erstellt den Token
    @Test
    void resetTokenErstellen() {
        String token = resetService.ResetTokenErstellen("123456");
        ForgotPassword saved =
                resetPasswortRepository.findByToken(token).orElseThrow();

        assertThat(saved.getMatrikelnummer()).isEqualTo("123456");
        assertThat(saved.getExpiresAt())
                .isAfter(LocalDateTime.now())
                .isBefore(LocalDateTime.now().plusMinutes(31));
    }

    // Der Token ist valid und das Passwort wird erneuert. Der Token wird geloescht.
    @Test
    void updatesPassword() {

        Kandidat kandidat = new Kandidat();
        kandidat.setMatrikelnummer("999999");
        kandidat.setVorname("Max");
        kandidat.setNachname("Mustermann");
        kandidat.setPassword(passwordEncoder.encode("oldPassword"));

        kandidatenRepository.save(kandidat);
        ForgotPassword token = new ForgotPassword();
        token.setToken("valid-token");
        token.setMatrikelnummer("999999");
        token.setExpiresAt(LocalDateTime.now().plusMinutes(10));

        resetPasswortRepository.save(token);
        boolean result = resetService.resetPassword("valid-token", "newPassword"); assertThat(result).isTrue();

        Kandidat updated = kandidatenRepository.findByMatrikelnummer("999999").orElseThrow();

        assertThat(passwordEncoder.matches("newPassword", updated.getPassword())).isTrue();

        assertThat(resetPasswortRepository.findByToken("valid-token")).isEmpty();
    }

// Der Token exsistiert nicht --> exception
    @Test
    void tokenException() {

        assertThatThrownBy(() -> resetService.resetPassword("missing-token", "pw")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Invalider Token");
    }

    @Test
    void resetPassword_tokenExpired_throwsException() {

        ForgotPassword token = new ForgotPassword();
        token.setToken("expired-token");
        token.setMatrikelnummer("123456");
        token.setExpiresAt(LocalDateTime.now().minusMinutes(1));

        resetPasswortRepository.save(token);

        assertThatThrownBy(() -> resetService.resetPassword("expired-token", "pw")).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Token zu alt");
    }

    // Der Kandidat exsistiert nicht --> exception
    @Test
    void KandidatException() {

        ForgotPassword token = new ForgotPassword();
        token.setToken("token");
        token.setMatrikelnummer("000000");
        token.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        resetPasswortRepository.save(token);

        assertThatThrownBy(() -> resetService.resetPassword("token", "pw")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Kandidat nicht gefunden");
    }
}

