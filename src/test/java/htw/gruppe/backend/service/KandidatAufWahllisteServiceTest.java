package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.Gremium;
import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.entity.KandidatAufWahlliste;
import htw.gruppe.backend.entity.Wahlliste;
import htw.gruppe.backend.repository.GremiumRepository;
import htw.gruppe.backend.repository.KandidatAufWahllisteRepository;
import htw.gruppe.backend.repository.KandidatenRepository;
import htw.gruppe.backend.repository.WahllisteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class KandidatAufWahllisteServiceTest {

    @Autowired
    private KandidatAufWahllisteService service;

    @Autowired
    private KandidatenRepository kandidatenRepository;

    @Autowired
    private WahllisteRepository wahllisteRepository;

    @Autowired
    private KandidatAufWahllisteRepository kandidatAufWahllisteRepository;

    @Autowired
    private GremiumRepository gremiumRepository;

    private Kandidat kandidat;
    private Wahlliste wahlliste;
    private Wahlliste wahlliste2;
    private Gremium gremium;

    @BeforeEach
    void setUp() {
        gremium = new Gremium();
        gremium.setName("Test Gremium");
       gremium = gremiumRepository.save(gremium);

        kandidat = new Kandidat();
        kandidat.setMatrikelnummer("M12345");
        kandidat.setVorname("Max");
        kandidat.setNachname("Mustermann");
        kandidat = kandidatenRepository.save(kandidat);

        wahlliste = new Wahlliste();
        wahlliste.setName("Test Wahlliste");
        wahlliste.setGremium(gremium);
        wahlliste.setValid(true);
        wahlliste = wahllisteRepository.save(wahlliste);

        wahlliste2 = new Wahlliste();
        wahlliste2.setName("Test Wahlliste2");
        wahlliste2.setGremium(gremium);
        wahlliste2.setValid(true);
        wahlliste2 = wahllisteRepository.save(wahlliste2);
    }

    // Einen Kandidaten zur Wahlliste hinzufuegen
    @Test
    void AddKandidatToWahlliste() {
        service.addKandidatToWahlliste(kandidat.getId(), wahlliste.getId());
        assertTrue(kandidatAufWahllisteRepository.existsByKandidatAndWahlliste(kandidat, wahlliste)); }

    // Hinzufuegen aber der Kandidat exsistiert bereits
    @Test
    void AddKandidatAlreadyOnWahllisteException() {
        kandidatAufWahllisteRepository.save(new KandidatAufWahlliste(kandidat, wahlliste));
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.addKandidatToWahlliste(kandidat.getId(), wahlliste.getId()));
        assertEquals("Kandidat ist bereits auf dieser Wahlliste", exception.getMessage());
    }

    // Kandidat von Wahlliste entferhnen. Database Restriction: eine Wahlliste muss >= 3 Kandidaten haben.
    @Test
    void RemoveKandidatFromWahlliste() {
        Kandidat k1 = new Kandidat();
        k1.setMatrikelnummer("M1");
        k1.setVorname("A");
        k1.setNachname("A");
        k1 = kandidatenRepository.save(k1);
        Kandidat k2 = new Kandidat();
        k2.setMatrikelnummer("M2");
        k2.setVorname("B");
        k2.setNachname("B");
        k2 = kandidatenRepository.save(k2);
        Kandidat k3 = new Kandidat();
        k3.setMatrikelnummer("M3");
        k3.setVorname("C");
        k3.setNachname("C");
        k3 = kandidatenRepository.save(k3);

        kandidatAufWahllisteRepository.save(new KandidatAufWahlliste(kandidat, wahlliste));
        kandidatAufWahllisteRepository.save(new KandidatAufWahlliste(k1, wahlliste));
        kandidatAufWahllisteRepository.save(new KandidatAufWahlliste(k2, wahlliste));
        kandidatAufWahllisteRepository.save(new KandidatAufWahlliste(k3, wahlliste));

        kandidatAufWahllisteRepository.save(new KandidatAufWahlliste(kandidat, wahlliste2));
        kandidatAufWahllisteRepository.save(new KandidatAufWahlliste(k1, wahlliste2));
        kandidatAufWahllisteRepository.save(new KandidatAufWahlliste(k2, wahlliste2));
        kandidatAufWahllisteRepository.save(new KandidatAufWahlliste(k3, wahlliste2));


        service.removeKandidatFromWahlliste(kandidat.getId(), wahlliste.getId());

        assertFalse(kandidatAufWahllisteRepository.existsByKandidatAndWahlliste(kandidat, wahlliste));
    }

    // Kandidat entferhnen. Restriction: jeder Kandidat muss auf einer Wahlliste exsistieren.
    @Test
    void RemoveKandidatException() {
        kandidatAufWahllisteRepository.save(new KandidatAufWahlliste(kandidat, wahlliste));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                service.removeKandidatFromWahlliste(kandidat.getId(), wahlliste.getId()));
        assertEquals("Ein Kandidat muss mindestens auf einer Wahlliste stehen", exception.getMessage());
    }
}



