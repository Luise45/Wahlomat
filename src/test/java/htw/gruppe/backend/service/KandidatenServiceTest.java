package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.repository.KandidatenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class KandidatenServiceTest {

    @Autowired
    private KandidatenService service;

    @Autowired
    private KandidatenRepository kandidatenRepository;

    private Kandidat kandidat;

    @BeforeEach
    void setUp() {
        // Create a Kandidat for testing
        kandidat = new Kandidat();
        kandidat.setMatrikelnummer("s0123456");
        kandidat.setVorname("Anna");
        kandidat.setNachname("annA");
        kandidat = kandidatenRepository.save(kandidat);
    }

    @Test
    void GetKandidatByMatrikelnummer() {
        Optional<Kandidat> result = service.getKandidatByMatrikelnummer("s0123456");
        assertTrue(result.isPresent());
        assertEquals("Anna", result.get().getVorname());
        assertEquals("annA", result.get().getNachname());
    }

    @Test
    void GetKandidatByMatrikelnummerNot() {
        Optional<Kandidat> result = service.getKandidatByMatrikelnummer("676767");
        assertFalse(result.isPresent());
    }

    @Test
    void AddKandidat() {
        Kandidat newKandidat = new Kandidat();
        newKandidat.setMatrikelnummer("s023457");
        newKandidat.setVorname("Maria");
        newKandidat.setNachname("Schmidt");

        Kandidat saved = service.addKandidat(newKandidat);

        assertNotNull(saved.getId());
        assertEquals("Maria", saved.getVorname());
        assertEquals("Schmidt", saved.getNachname());

        Optional<Kandidat> fromDb = kandidatenRepository.findByMatrikelnummer("s023457");
        assertTrue(fromDb.isPresent());
        assertEquals("Maria", fromDb.get().getVorname());
    }

    @Test
    void AddKandidatDuplicateMatrikelnummerThrows() {
        Kandidat duplicate = new Kandidat();
        duplicate.setMatrikelnummer("s0123456");
        duplicate.setVorname("Duplicate");
        duplicate.setNachname("Kandidat");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                service.addKandidat(duplicate)
        );

        assertEquals("Kandidat mit dieser Matrikelnummer existiert bereits", exception.getMessage());
    }
}

