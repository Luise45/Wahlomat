package htw.gruppe.backend.repository;

import htw.gruppe.backend.entity.Gremium;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class GremiumRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GremiumRepository gremiumRepository;

    private Gremium mitFachbereich;
    private Gremium ohneFachbereich;

    @BeforeEach
    void setUp() {
        gremiumRepository.deleteAll();

        mitFachbereich = new Gremium("Fachbereichsrat");
        mitFachbereich.setRequiresFachbereich(true);

        ohneFachbereich = new Gremium("Wahlausschuss");
        ohneFachbereich.setRequiresFachbereich(false);

        entityManager.persist(mitFachbereich);
        entityManager.persist(ohneFachbereich);
        entityManager.flush();
    }

    @Test
    void findByName_WhenExists_ShouldReturnGremium() {
        Optional<Gremium> result = gremiumRepository.findByName("Fachbereichsrat");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Fachbereichsrat");
    }

    @Test
    void findByName_WhenNotExists_ShouldReturnEmpty() {
        Optional<Gremium> result = gremiumRepository.findByName("NichtVorhanden");

        assertThat(result).isNotPresent();
    }

    @Test
    void existsByName_ShouldReturnTrueOrFalse() {
        assertThat(gremiumRepository.existsByName("Fachbereichsrat")).isTrue();
        assertThat(gremiumRepository.existsByName("UnbekanntesGremium")).isFalse();
    }

    @Test
    void findByRequiresFachbereichTrue_ShouldReturnOnlyMatching() {
        List<Gremium> result = gremiumRepository.findByRequiresFachbereichTrue();

        assertThat(result)
                .hasSize(1)
                .allMatch(Gremium::isRequiresFachbereich)
                .extracting(Gremium::getName)
                .containsExactly("Fachbereichsrat");
    }

    @Test
    void findByRequiresFachbereichTrue_ShouldReturnEmptyIfNone() {
        entityManager.remove(mitFachbereich);
        entityManager.flush();

        List<Gremium> result = gremiumRepository.findByRequiresFachbereichTrue();
        assertThat(result).isEmpty();
    }
    @Test
    void saveAndDelete_ShouldPersistAndRemoveGremium() {
        Gremium neu = new Gremium("NeuesGremium");
        neu.setRequiresFachbereich(true);

        // speichern
        entityManager.persist(neu);
        entityManager.flush();

        Optional<Gremium> found = gremiumRepository.findByName("NeuesGremium");
        assertThat(found).isPresent();
        assertThat(found.get().isRequiresFachbereich()).isTrue();

        // löschen
        gremiumRepository.delete(neu);
        entityManager.flush();

        assertThat(gremiumRepository.findByName("NeuesGremium")).isNotPresent();
    }
    @Test
    void findByRequiresFachbereichTrue_ShouldReturnAllMatching() {
        Gremium gremium2 = new Gremium("ZweiterFachbereich");
        gremium2.setRequiresFachbereich(true);
        entityManager.persist(gremium2);
        entityManager.flush();

        List<Gremium> result = gremiumRepository.findByRequiresFachbereichTrue();
        assertThat(result).hasSize(2)
                .allMatch(Gremium::isRequiresFachbereich)
                .extracting(Gremium::getName)
                .containsExactlyInAnyOrder("Fachbereichsrat", "ZweiterFachbereich");
    }

}
