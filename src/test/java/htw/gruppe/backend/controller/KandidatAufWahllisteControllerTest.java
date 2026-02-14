package htw.gruppe.backend.controller;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")

class KandidatAufWahllisteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KandidatAufWahllisteRepository kandidatAufWahllisteRepository;

    @Autowired
    private KandidatenRepository kandidatenRepository;

    @Autowired
    private WahllisteRepository wahllisteRepository;

    @Autowired
    private GremiumRepository gremiumRepository;

    @BeforeEach
    void setUp() {
        kandidatAufWahllisteRepository.deleteAll();
        kandidatenRepository.deleteAll();
        wahllisteRepository.deleteAll();
        gremiumRepository.deleteAll();
    }

    @Test
    void addKandidat() throws Exception {
        Gremium gremium = gremiumRepository.save(new Gremium("Test-Gremium"));
        Wahlliste wahlliste = wahllisteRepository.save(new Wahlliste("Liste A", gremium));
        Kandidat kandidat = kandidatenRepository.save(new Kandidat("123456", "1", "Max", null, "Muster", "Beschreibung", "Computer Engineering (B.Sc.)"));

        mockMvc.perform(
                post("/api/wahllisten/{wahllisteId}/kandidaten/{kandidatId}",
                        wahlliste.getId(), kandidat.getId())
        ).andExpect(status().isOk());
    }

    @Test
    void removeKandidat() throws Exception {
        Gremium gremium = gremiumRepository.save(new Gremium("Test-Gremium"));

        Wahlliste wahlliste = wahllisteRepository.save(new Wahlliste("Liste A", gremium));
        Wahlliste wahlliste2 = wahllisteRepository.save(new Wahlliste("Liste A", gremium));

        Kandidat kandidat = kandidatenRepository.save(
                new Kandidat("123456", "1", "Max", null, "Muster", "Beschreibung", "Computer Engineering (B.Sc.)")
        );
        Kandidat kandidat2 = kandidatenRepository.save(
                new Kandidat("134567", "1", "Anna", null, "Muster", "Beschreibung", "Computer Engineering (B.Sc.)")
        );
        Kandidat kandidat3 = kandidatenRepository.save(
                new Kandidat("676767", "1", "Lenna", null, "Muster", "Beschreibung", "Computer Engineering (B.Sc.)")
        );
        Kandidat kandidat4 = kandidatenRepository.save(
                new Kandidat("111111", "1", "Mary", null, "Muster", "Beschreibung", "Computer Engineering (B.Sc.)")
        );

        KandidatAufWahlliste kaul = kandidatAufWahllisteRepository.save(
                new KandidatAufWahlliste(kandidat, wahlliste)
        );
        KandidatAufWahlliste kaul2 = kandidatAufWahllisteRepository.save(
                new KandidatAufWahlliste(kandidat2, wahlliste)
        );
        KandidatAufWahlliste kaul4 = kandidatAufWahllisteRepository.save(
                new KandidatAufWahlliste(kandidat3, wahlliste)
        );
        KandidatAufWahlliste kaul5 = kandidatAufWahllisteRepository.save(
                new KandidatAufWahlliste(kandidat4, wahlliste)
        );

        KandidatAufWahlliste kaul3 = kandidatAufWahllisteRepository.save(
                new KandidatAufWahlliste(kandidat, wahlliste2)
        );


        mockMvc.perform(delete("/api/wahllisten/{wahllisteId}/kandidaten/{kandidatId}",
                        wahlliste.getId(), kandidat.getId()))
                .andExpect(status().isNoContent());

        assertFalse(kandidatAufWahllisteRepository.findAll().isEmpty());
    }

}
