package htw.gruppe.backend.service;
import htw.gruppe.backend.entity.Aussage;
import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.entity.KandidatenAntwort;
import htw.gruppe.backend.record.KandidatenAntwortenDto;
import htw.gruppe.backend.record.KandidatenAntwortenRequestDto;
import htw.gruppe.backend.record.KandidatenAntwortenResponseDto;
import htw.gruppe.backend.repository.AussagenRepository;
import htw.gruppe.backend.repository.KandidatenAntwortRepository;
import htw.gruppe.backend.repository.KandidatenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class KandidatenAntwortServiceTest {

    @Autowired
    private KandidatenAntwortService service;

    @Autowired
    private KandidatenRepository kandidatenRepository;

    @Autowired
    private AussagenRepository aussagenRepository;

    @Autowired
    private KandidatenAntwortRepository kandidatenAntwortRepository;

    private Kandidat kandidat;
    private Aussage aussage;

    @BeforeEach
    void setUp() {
        kandidat = new Kandidat();
        kandidat.setMatrikelnummer("s012345");
        kandidat.setVorname("Lenna");
        kandidat.setNachname("L");
        kandidat = kandidatenRepository.save(kandidat);

        aussage = new Aussage();
        aussage.setAussage_text("Test Aussage");
        aussage = aussagenRepository.save(aussage);
    }

    @Test
    void PostKandidatenAntwort() {
        KandidatenAntwortenRequestDto request = new KandidatenAntwortenRequestDto(aussage.getId(), 5);

        KandidatenAntwortenResponseDto response = service.postKandidatenAntwort(request, kandidat.getMatrikelnummer());
        assertNotNull(response);
        assertEquals(aussage.getId(), response.aussage_id());
        assertEquals(5, response.answerValue());

        List<KandidatenAntwort> saved = kandidatenAntwortRepository.findByKandidat_IdOrderByAussage_IdAsc(kandidat.getId());
        assertEquals(1, saved.size());
        assertEquals(5, saved.get(0).getAnswerValue());
    }

    @Test
    void GetKandidatenAntwortById() {
        KandidatenAntwort antwort = new KandidatenAntwort(kandidat, aussage, 4);
        kandidatenAntwortRepository.save(antwort);
        List<KandidatenAntwortenDto> result = service.getKandidatenAntwort(kandidat.getId());

        assertEquals(1, result.size());
        assertEquals(aussage.getId(), result.get(0).aussageId());
        assertEquals(4, result.get(0).answerValue());
    }

    @Test
    void GetKandidatenAntwortByKandidat() {
        KandidatenAntwort antwort = new KandidatenAntwort(kandidat, aussage, 3);
        kandidatenAntwortRepository.save(antwort);
        List<KandidatenAntwortenDto> result = service.getKandidatenAntwortByBenutzer(kandidat.getMatrikelnummer());

        assertEquals(1, result.size());
        assertEquals(aussage.getId(), result.get(0).aussageId());
        assertEquals(3, result.get(0).answerValue());
    }

    @Test
    void InvalidMatrikelnummerexception() {
        KandidatenAntwortenRequestDto request = new KandidatenAntwortenRequestDto(aussage.getId(), 2);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.postKandidatenAntwort(request, "INVALID_MATR_NR"));
        assertEquals("Kandidat nich hier", exception.getMessage()); }

    @Test
    void InvalidAussageIdException() {
        KandidatenAntwortenRequestDto request = new KandidatenAntwortenRequestDto(999L, 2);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.postKandidatenAntwort(request, kandidat.getMatrikelnummer()));

        assertEquals("Aussage nicht gefunden", exception.getMessage()); }
}

