package htw.gruppe.backend.repository;

import htw.gruppe.backend.entity.Aussage;
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
class AussagenRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AussagenRepository aussagenRepository;

    Aussage aktiv1;
    Aussage aktiv2;
    Aussage inaktiv;

    @BeforeEach
    void setUp() {

        // IDs auf null lassen, damit H2 sie generiert
        aktiv1 = new Aussage(null, "Aktive Aussage 1", true);
        aktiv2 = new Aussage(null, "Aktive Aussage 2", true);
        inaktiv = new Aussage(null, "Inaktive Aussage", false);

        entityManager.persist(aktiv1);
        entityManager.persist(aktiv2);
        entityManager.persist(inaktiv);
        entityManager.flush();
    }

    @Test
    void findByAktivTrue_ShouldReturnOnlyActiveAussagen() {
        List<Aussage> result = aussagenRepository.findByAktivTrue();

        assertThat(result).hasSize(2)
                .allMatch(Aussage::getAktiv)
                .extracting(Aussage::getAussage_text)
                .containsExactlyInAnyOrder("Aktive Aussage 1", "Aktive Aussage 2");
    }

    @Test
    void findByAktivTrue_ShouldReturnEmptyIfNoneActive() {
        // alle auf false setzen
        aktiv1.setAktiv(false);
        aktiv2.setAktiv(false);
        inaktiv.setAktiv(false);

        entityManager.persist(aktiv1);
        entityManager.persist(aktiv2);
        entityManager.persist(inaktiv);
        entityManager.flush();

        List<Aussage> result = aussagenRepository.findByAktivTrue();
        assertThat(result).isEmpty();
    }

    @Test
    void findById_ShouldReturnAussage() {
        Optional<Aussage> found = aussagenRepository.findById(aktiv1.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getAussage_text()).isEqualTo("Aktive Aussage 1");
    }

    @Test
    void existsById_ShouldReturnTrueOrFalse() {
        assertThat(aussagenRepository.existsById(aktiv1.getId())).isTrue();
        assertThat(aussagenRepository.existsById(999L)).isFalse();
    }

    @Test
    void count_ShouldReturnCorrectNumber() {
        assertThat(aussagenRepository.count()).isEqualTo(3);
    }

    @Test
    void save_ShouldPersistAussage() {
        Aussage neueAussage = new Aussage(null, "Neue Aussage", true);
        aussagenRepository.save(neueAussage);

        assertThat(neueAussage.getId()).isNotNull(); // ID wurde generiert

        Optional<Aussage> found = aussagenRepository.findById(neueAussage.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getAussage_text()).isEqualTo("Neue Aussage");
        assertThat(found.get().getAktiv()).isTrue();
    }

    @Test
    void delete_ShouldRemoveAussage() {
        aussagenRepository.delete(aktiv1);
        assertThat(aussagenRepository.findById(aktiv1.getId())).isNotPresent();
    }

    @Test
    void deleteById_ShouldRemoveAussage() {
        aussagenRepository.deleteById(aktiv2.getId());
        assertThat(aussagenRepository.findById(aktiv2.getId())).isNotPresent();
        assertThat(aussagenRepository.count()).isEqualTo(2); // noch 2 Aussagen übrig
    }

    @Test
    void testNullAndEmptyFields() {
        // Gute Fälle: aktives Objekt
        Aussage aussage = new Aussage(null, "Test", true);
        aussagenRepository.save(aussage);
        assertThat(aussage.getId()).isNotNull();

        // Schlechte Fälle: leeres Feld oder null
        Aussage leereAussage = new Aussage(null, null, false);
        aussagenRepository.save(leereAussage);
        assertThat(leereAussage.getId()).isNotNull(); // ID generiert, auch wenn Text null
        assertThat(leereAussage.getAussage_text()).isNull();
        assertThat(leereAussage.getAktiv()).isFalse();
    }
}
