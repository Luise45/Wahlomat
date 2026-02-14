package htw.gruppe.backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import htw.gruppe.backend.entity.Gremium;
import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.entity.Wahlliste;
import htw.gruppe.backend.repository.GremiumRepository;
import htw.gruppe.backend.repository.KandidatenRepository;
import htw.gruppe.backend.repository.WahllisteRepository;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class AdminKandidatAufWahllisteControllerTest {
        
    @Autowired
    MockMvc mockMvc;

    @Autowired
    GremiumRepository gremiumRepository;

    @Autowired
    WahllisteRepository wahllisteRepository;

    @Autowired
    KandidatenRepository kandidatenRepository;

    @BeforeEach
    void setUp() {
        wahllisteRepository.deleteAll();
        kandidatenRepository.deleteAll();
        gremiumRepository.deleteAll();
    }

    @Test
    void getAllMappings_ok() throws Exception {
        mockMvc.perform(get("/api/admin/wahllisten/zuordnungen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void addKandidat_ok() throws Exception {
        Gremium g = gremiumRepository.save(new Gremium("Gremium A"));
        Wahlliste w = wahllisteRepository.save(new Wahlliste("Liste A", g));

        Kandidat k = kandidatenRepository.save(
                new Kandidat("m1", "FB", "Max", "pw", "Mustermann", "desc", "INF")
        );
        k.setRole("student");

        mockMvc.perform(post("/api/admin/wahllisten/" + w.getId() + "/kandidaten/" + k.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void addKandidat_error_returns5xx() throws Exception {
        mockMvc.perform(post("/api/admin/wahllisten/9999/kandidaten/9999"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void removeKandidat_error_returns5xx() throws Exception {
        Gremium g = gremiumRepository.save(new Gremium("Gremium A"));
        Wahlliste w = wahllisteRepository.save(new Wahlliste("Liste A", g));

        mockMvc.perform(delete("/api/admin/wahllisten/" + w.getId() + "/kandidaten/9999"))
                .andExpect(status().is5xxServerError());
    }
}

