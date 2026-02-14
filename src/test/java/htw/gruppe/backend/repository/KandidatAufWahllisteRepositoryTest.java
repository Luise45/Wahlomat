package htw.gruppe.backend.repository;

import htw.gruppe.backend.entity.Gremium;
import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.entity.KandidatAufWahlliste;
import htw.gruppe.backend.entity.Wahlliste;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class KandidatAufWahllisteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private KandidatAufWahllisteRepository repository;

    @Autowired
    private KandidatenRepository kandidatenRepository;

    @Autowired
    private WahllisteRepository wahllisteRepository;

    @Autowired
    private GremiumRepository gremiumRepository;

    private Gremium gremium;
    private Wahlliste wahlliste;
    private Kandidat kandidat;
    private KandidatAufWahlliste kandidatAufWahlliste;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        kandidatenRepository.deleteAll();
        wahllisteRepository.deleteAll();
        gremiumRepository.deleteAll();

        gremium = new Gremium("Gremium A");
        entityManager.persist(gremium);

        wahlliste = new Wahlliste("Liste A", gremium);
        entityManager.persist(wahlliste);

        kandidat = new Kandidat("123456", "1", "Max", null, "Muster", "Beschreibung", "Informatik");
        entityManager.persist(kandidat);

        kandidatAufWahlliste = new KandidatAufWahlliste(kandidat, wahlliste);
        entityManager.persist(kandidatAufWahlliste);

        entityManager.flush();
    }

    @Test
    void existsByKandidatAndWahlliste_ShouldReturnTrueOrFalse() {
        // Positiv-Fall
        assertThat(repository.existsByKandidatAndWahlliste(kandidat, wahlliste)).isTrue();

        // Negativ-Fall
        Kandidat andererKandidat = new Kandidat("999999", "1", "Anna", null, "Test", "Beschreibung", "Informatik");
        entityManager.persist(andererKandidat);
        Wahlliste andereListe = new Wahlliste("Liste B", gremium);
        entityManager.persist(andereListe);
        entityManager.flush();

        assertThat(repository.existsByKandidatAndWahlliste(andererKandidat, andereListe)).isFalse();
    }

    @Test
    void countByKandidatId_ShouldReturnCorrect() {
        assertThat(repository.countByKandidatId(kandidat.getId())).isEqualTo(1);

        // Negativ-Fall
        assertThat(repository.countByKandidatId(999999L)).isZero();
    }

    @Test
    void countByWahllisteId_ShouldReturnCorrect() {
        assertThat(repository.countByWahllisteId(wahlliste.getId())).isEqualTo(1);

        // Negativ-Fall
        assertThat(repository.countByWahllisteId(999999L)).isZero();
    }

    @Test
    void deleteByKandidatIdAndWahllisteId_ShouldRemoveEntry() {
        repository.deleteByKandidatIdAndWahllisteId(kandidat.getId(), wahlliste.getId());
        assertThat(repository.count()).isZero();

        // Negativ-Fall: löschen von nicht vorhandenem Eintrag
        repository.deleteByKandidatIdAndWahllisteId(999L, 999L);
        assertThat(repository.count()).isZero();
    }

    @Test
    void findByKandidatIdIn_ShouldReturnMatchingEntriesOrEmpty() {
        // Positiv-Fall
        List<KandidatAufWahlliste> result = repository.findByKandidatIdIn(List.of(kandidat.getId()));
        assertThat(result).hasSize(1).contains(kandidatAufWahlliste);

        // Negativ-Fall
        result = repository.findByKandidatIdIn(List.of(999999L));
        assertThat(result).isEmpty();
    }
    @Test
    void existsByKandidatIdAndWahllisteGremiumId_ShouldReturnTrueOrFalse() {
        // Positiv-Fall
        assertThat(repository.existsByKandidatIdAndWahllisteGremiumId(kandidat.getId(), gremium.getId())).isTrue();

        // Negativ-Fall: falscher Kandidat
        assertThat(repository.existsByKandidatIdAndWahllisteGremiumId(999999L, gremium.getId())).isFalse();

        // Negativ-Fall: falsches Gremium
        Gremium anderesGremium = new Gremium("Gremium B");
        entityManager.persist(anderesGremium);
        entityManager.flush();
        assertThat(repository.existsByKandidatIdAndWahllisteGremiumId(kandidat.getId(), anderesGremium.getId())).isFalse();
    }
    @Test
    void deleteByKandidatIdAndWahllisteId_ShouldOnlyRemoveSpecificEntry() {
        Kandidat kandidat2 = new Kandidat("888888", "1", "Anna", null, "Test", "Beschreibung", "Informatik");
        entityManager.persist(kandidat2);

        KandidatAufWahlliste ka2 = new KandidatAufWahlliste(kandidat2, wahlliste);
        entityManager.persist(ka2);
        entityManager.flush();

        // Löschen von kandidatAufWahlliste
        repository.deleteByKandidatIdAndWahllisteId(kandidat.getId(), wahlliste.getId());
        entityManager.flush();

        // Nur ka2 sollte noch vorhanden sein
        List<KandidatAufWahlliste> remaining = repository.findAll();
        assertThat(remaining).hasSize(1).contains(ka2);
    }
    @Test
    void findByKandidatIdIn_ShouldReturnMultipleMatchingEntries() {
        Kandidat kandidat2 = new Kandidat("888888", "1", "Anna", null, "Test", "Beschreibung", "Informatik");
        entityManager.persist(kandidat2);

        KandidatAufWahlliste ka2 = new KandidatAufWahlliste(kandidat2, wahlliste);
        entityManager.persist(ka2);
        entityManager.flush();

        List<KandidatAufWahlliste> result = repository.findByKandidatIdIn(List.of(kandidat.getId(), kandidat2.getId()));
        assertThat(result).hasSize(2).contains(kandidatAufWahlliste, ka2);
    }

}
