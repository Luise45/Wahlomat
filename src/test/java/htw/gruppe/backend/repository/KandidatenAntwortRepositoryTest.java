package htw.gruppe.backend.repository;

import htw.gruppe.backend.entity.Aussage;
import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.entity.KandidatenAntwort;
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
class KandidatenAntwortRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private KandidatenAntwortRepository repository;

    @Autowired
    private KandidatenRepository kandidatenRepository;

    @Autowired
    private AussagenRepository aussagenRepository;

    private Kandidat kandidat;
    private Aussage aussage1;
    private Aussage aussage2;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        kandidatenRepository.deleteAll();
        aussagenRepository.deleteAll();

        // Kandidat persistieren
        kandidat = new Kandidat("123456", "1", "Max", null, "Muster", "Beschreibung", "Informatik");
        entityManager.persist(kandidat);

        // Aussagen persistieren (IDs automatisch generiert)
        aussage1 = new Aussage(null, "Aussage A", true);
        aussage2 = new Aussage(null, "Aussage B", true);
        entityManager.persist(aussage1);
        entityManager.persist(aussage2);

        // KandidatenAntworten persistieren
        entityManager.persist(new KandidatenAntwort(kandidat, aussage1, 1));
        entityManager.persist(new KandidatenAntwort(kandidat, aussage2, 2));

        entityManager.flush();
    }

    @Test
    void findByKandidatIdOrderByAussageId_ShouldReturnAnswersInOrder() {
        List<KandidatenAntwort> result = repository.findByKandidat_IdOrderByAussage_IdAsc(kandidat.getId());

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getAussage().getId()).isEqualTo(aussage1.getId());
        assertThat(result.get(0).getAnswerValue()).isEqualTo(1);
        assertThat(result.get(1).getAussage().getId()).isEqualTo(aussage2.getId());
        assertThat(result.get(1).getAnswerValue()).isEqualTo(2);
    }

    @Test
    void findByKandidatIdOrderByAussageId_ShouldReturnEmptyIfNoAnswers() {
        Kandidat neuerKandidat = new Kandidat("999999", "1", "Anna", null, "Muster", "Beschreibung", "Informatik");
        entityManager.persist(neuerKandidat);
        entityManager.flush();

        List<KandidatenAntwort> result = repository.findByKandidat_IdOrderByAussage_IdAsc(neuerKandidat.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void findById_ShouldReturnOptional() {
        KandidatenAntwort ka = repository.findByKandidat_IdOrderByAussage_IdAsc(kandidat.getId()).get(0);
        Optional<KandidatenAntwort> found = repository.findById(ka.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getAnswerValue()).isEqualTo(ka.getAnswerValue());
    }

    @Test
    void delete_ShouldRemoveAnswer() {
        KandidatenAntwort ka = repository.findByKandidat_IdOrderByAussage_IdAsc(kandidat.getId()).get(0);
        repository.delete(ka);
        entityManager.flush();

        List<KandidatenAntwort> remaining = repository.findByKandidat_IdOrderByAussage_IdAsc(kandidat.getId());
        assertThat(remaining).hasSize(1)
                .noneMatch(a -> a.getId().equals(ka.getId()));
    }

    @Test
    void multipleKandidaten_ShouldReturnSeparateAnswers() {
        Kandidat kandidat2 = new Kandidat("999999", "1", "Anna", null, "Muster", "Beschreibung", "Informatik");
        entityManager.persist(kandidat2);
        entityManager.persist(new KandidatenAntwort(kandidat2, aussage1, 3));
        entityManager.flush();

        List<KandidatenAntwort> answersKandidat1 = repository.findByKandidat_IdOrderByAussage_IdAsc(kandidat.getId());
        List<KandidatenAntwort> answersKandidat2 = repository.findByKandidat_IdOrderByAussage_IdAsc(kandidat2.getId());

        assertThat(answersKandidat1).hasSize(2);
        assertThat(answersKandidat2).hasSize(1)
                .extracting(KandidatenAntwort::getAnswerValue)
                .containsExactly(3);
    }

    @Test
    void save_ShouldPersistAnswer() {
        Kandidat neuerKandidat = new Kandidat("888888", "1", "Peter", null, "Müller", "Beschreibung", "Mathematik");
        entityManager.persist(neuerKandidat);

        Aussage neueAussage = new Aussage(null, "Neue Aussage", true);
        entityManager.persist(neueAussage);
        entityManager.flush();

        KandidatenAntwort neueAntwort = new KandidatenAntwort(neuerKandidat, neueAussage, 5);
        KandidatenAntwort saved = repository.save(neueAntwort);
        entityManager.flush();

        Optional<KandidatenAntwort> found = repository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getAnswerValue()).isEqualTo(5);
        assertThat(found.get().getKandidat().getMatrikelnummer()).isEqualTo("888888");
        assertThat(found.get().getAussage().getAussage_text()).isEqualTo("Neue Aussage");
    }

    @Test
    void existsById_ShouldReturnTrueOrFalse() {
        KandidatenAntwort ka = repository.findByKandidat_IdOrderByAussage_IdAsc(kandidat.getId()).get(0);

        assertThat(repository.existsById(ka.getId())).isTrue();
        assertThat(repository.existsById(9999L)).isFalse();
    }
}
