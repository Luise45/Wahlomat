package htw.gruppe.backend.repository;

import htw.gruppe.backend.entity.ForgotPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ResetPasswortRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ResetPasswortRepository resetPasswortRepository;

    private ForgotPassword forgotPassword;

    @BeforeEach
    void setUp() {
        resetPasswortRepository.deleteAll();

        forgotPassword = new ForgotPassword();
        forgotPassword.setToken("reset123");

        entityManager.persist(forgotPassword);
        entityManager.flush();
    }

    @Test
    void findByToken_ShouldReturnIfExists() {
        Optional<ForgotPassword> found = resetPasswortRepository.findByToken("reset123");

        assertThat(found).isPresent();
        assertThat(found.get().getToken()).isEqualTo("reset123");
    }

    @Test
    void findByToken_ShouldReturnEmptyIfNotExists() {
        Optional<ForgotPassword> found = resetPasswortRepository.findByToken("nichtVorhanden");

        assertThat(found).isNotPresent();
    }

    @Test
    void existsById_ShouldReturnTrueForSavedToken() {
        assertThat(resetPasswortRepository.existsById(forgotPassword.getId())).isTrue();
    }

    @Test
    void delete_ShouldRemoveToken() {
        resetPasswortRepository.delete(forgotPassword);

        assertThat(resetPasswortRepository.findByToken("reset123")).isNotPresent();
        assertThat(resetPasswortRepository.count()).isZero();
    }

    @Test
    void findByTokenNull_ShouldReturnEmpty() {
        Optional<ForgotPassword> result = resetPasswortRepository.findByToken(null);
        assertThat(result).isNotPresent();
    }
    @Test
    void deleteById_ShouldRemoveToken() {
        resetPasswortRepository.deleteById(forgotPassword.getId());
        assertThat(resetPasswortRepository.findByToken("reset123")).isNotPresent();
    }



}
