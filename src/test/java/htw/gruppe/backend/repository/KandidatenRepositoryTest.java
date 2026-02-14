package htw.gruppe.backend.repository;

import htw.gruppe.backend.entity.Kandidat;
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
class KandidatenRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private KandidatenRepository kandidatenRepository;

    private Kandidat kandidat;

    @BeforeEach
    void setUp() {
        kandidatenRepository.deleteAll();

        kandidat = new Kandidat("123456", "1", "Max", "pass", "Muster", "Beschreibung", "Informatik");
        entityManager.persist(kandidat);
        entityManager.flush();
    }

    @Test
    void findByMatrikelnummer_ShouldReturnKandidat() {
        Optional<Kandidat> found = kandidatenRepository.findByMatrikelnummer("123456");

        assertThat(found).isPresent();
        assertThat(found.get().getVorname()).isEqualTo("Max");
        assertThat(found.get().getMatrikelnummer()).isEqualTo("123456");
    }

    @Test
    void findByMatrikelnummer_NonExisting_ShouldReturnEmpty() {
        Optional<Kandidat> found = kandidatenRepository.findByMatrikelnummer("999999");

        assertThat(found).isNotPresent();
    }

    @Test
    void save_ShouldPersistKandidat() {
        Kandidat newKandidat = new Kandidat("654321", "2", "Anna", "secret", "Musterfrau", "Beschreibung", "Mathe");
        kandidatenRepository.save(newKandidat);

        Optional<Kandidat> found = kandidatenRepository.findByMatrikelnummer("654321");
        assertThat(found).isPresent();
        assertThat(found.get().getVorname()).isEqualTo("Anna");
    }
    @Test
    void delete_ShouldRemoveKandidat() {
        kandidatenRepository.delete(kandidat);
        assertThat(kandidatenRepository.findByMatrikelnummer("123456")).isNotPresent();
        assertThat(kandidatenRepository.count()).isZero();
    }
    @Test
    void update_ShouldChangeVorname() {
        kandidat.setVorname("Maximilian");
        kandidatenRepository.save(kandidat);

        Optional<Kandidat> found = kandidatenRepository.findByMatrikelnummer("123456");
        assertThat(found).isPresent();
        assertThat(found.get().getVorname()).isEqualTo("Maximilian");
    }
    @Test
    void saveNullMatrikelnummer_ShouldThrowException() {
        Kandidat invalid = new Kandidat(null, "3", "Null", "pass", "User", "Desc", "Informatik");

    }

    @Test
    void existsByMatrikelnummer_ShouldReturnTrueOrFalse() {
        // Positiver Fall
        assertThat(kandidatenRepository.existsByMatrikelnummer("123456")).isTrue();

        // Negativer Fall
        assertThat(kandidatenRepository.existsByMatrikelnummer("999999")).isFalse();
    }

}
