package htw.gruppe.backend.repository;

import htw.gruppe.backend.entity.RegistrationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class RegistrationTokenRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RegistrationTokenRepository repository;

    private RegistrationToken token;

    @BeforeEach
    void setUp() {
        repository.deleteAll();

        token = new RegistrationToken();
        token.setToken("abc123");
        token.setMatrikelnummer("123456");
        token.setExpiresAt(LocalDateTime.now().plusHours(1));

        entityManager.persist(token);
        entityManager.flush();
    }

    @Test
    void findByToken_ShouldReturnToken() {
        Optional<RegistrationToken> found = repository.findByToken("abc123");

        assertThat(found).isPresent();
        assertThat(found.get().getMatrikelnummer()).isEqualTo("123456");
        assertThat(found.get().getToken()).isEqualTo("abc123");
    }

    @Test
    void findByToken_NonExisting_ShouldReturnEmpty() {
        Optional<RegistrationToken> found = repository.findByToken("nichtVorhanden");

        assertThat(found).isNotPresent();
    }

    @Test
    void save_ShouldPersistToken() {
        RegistrationToken newToken = new RegistrationToken();
        newToken.setToken("xyz789");
        newToken.setMatrikelnummer("654321");
        newToken.setExpiresAt(LocalDateTime.now().plusHours(2));

        entityManager.persist(newToken);
        entityManager.flush();

        Optional<RegistrationToken> found = repository.findByToken("xyz789");
        assertThat(found).isPresent();
        assertThat(found.get().getMatrikelnummer()).isEqualTo("654321");
    }
    @Test
    void saveWithoutId_ShouldGenerateId() {
        RegistrationToken newToken = new RegistrationToken();
        newToken.setToken("auto123");
        newToken.setMatrikelnummer("999999");
        newToken.setExpiresAt(LocalDateTime.now().plusHours(1));

        RegistrationToken saved = repository.save(newToken);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getToken()).isEqualTo("auto123");
    }
    @Test
    void update_ShouldChangeMatrikelnummer() {
        token.setMatrikelnummer("111111");
        repository.save(token);

        Optional<RegistrationToken> updated = repository.findByToken(token.getToken());
        assertThat(updated).isPresent();
        assertThat(updated.get().getMatrikelnummer()).isEqualTo("111111");
    }
    @Test
    void expiresAt_ShouldBeStoredCorrectly() {
        LocalDateTime future = LocalDateTime.now().plusDays(1);
        token.setExpiresAt(future);
        repository.save(token);

        Optional<RegistrationToken> found = repository.findByToken(token.getToken());
        assertThat(found).isPresent();
        assertThat(found.get().getExpiresAt()).isEqualTo(future);
    }


    @Test
    void delete_ShouldRemoveToken() {
        repository.delete(token);
        assertThat(repository.findByToken("abc123")).isNotPresent();
        assertThat(repository.count()).isZero();
    }

}
