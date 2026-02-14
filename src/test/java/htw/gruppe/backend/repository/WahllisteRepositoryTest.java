package htw.gruppe.backend.repository;

import htw.gruppe.backend.entity.Gremium;
import htw.gruppe.backend.entity.Wahlliste;
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
class WahllisteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WahllisteRepository repository;

    @Autowired
    private GremiumRepository gremiumRepository;

    private Gremium gremium;
    private Wahlliste wahlliste;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        gremiumRepository.deleteAll();

        gremium = gremiumRepository.save(new Gremium("Gremium Test"));
        wahlliste = new Wahlliste("Liste A", gremium);
        wahlliste.setValid(true);

        entityManager.persist(wahlliste);
        entityManager.flush();
    }

    @Test
    void save_ShouldPersistWahlliste() {
        assertThat(wahlliste.getId()).isNotNull();

        Optional<Wahlliste> found = repository.findById(wahlliste.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Liste A");
        assertThat(found.get().getGremium().getName()).isEqualTo("Gremium Test");
        assertThat(found.get().isValid()).isTrue();
    }

    @Test
    void findAll_ShouldReturnListOfWahllisten() {
        List<Wahlliste> lists = repository.findAll();
        assertThat(lists).hasSize(1).extracting(Wahlliste::getName).containsExactly("Liste A");
    }

    @Test
    void delete_ShouldRemoveWahlliste() {
        repository.delete(wahlliste);

        assertThat(repository.findById(wahlliste.getId())).isNotPresent();
        assertThat(repository.count()).isZero();
    }

    @Test
    void update_ShouldChangeName() {
        wahlliste.setName("Liste B");
        entityManager.persist(wahlliste);
        entityManager.flush();

        Optional<Wahlliste> updated = repository.findById(wahlliste.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getName()).isEqualTo("Liste B");
    }



}
