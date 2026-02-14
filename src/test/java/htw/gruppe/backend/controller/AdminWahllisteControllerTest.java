package htw.gruppe.backend.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import htw.gruppe.backend.entity.Gremium;
import htw.gruppe.backend.entity.Wahlliste;
import htw.gruppe.backend.repository.GremiumRepository;
import htw.gruppe.backend.repository.WahllisteRepository;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class AdminWahllisteControllerTest {

 @Autowired
    MockMvc mockMvc;

    @Autowired
    WahllisteRepository wahllisteRepository;

    @Autowired
    GremiumRepository gremiumRepository;

    @BeforeEach
    void setUp() {
        wahllisteRepository.deleteAll();
        gremiumRepository.deleteAll();
    }

    @Test
    void getAllWahllisten_ok() throws Exception {
        Gremium g = gremiumRepository.save(new Gremium("Gremium A"));
        wahllisteRepository.save(new Wahlliste("Liste A", g));

        mockMvc.perform(get("/api/admin/wahllisten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getWahlliste_error_returns5xx() throws Exception {
        mockMvc.perform(get("/api/admin/wahllisten/9999"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void createWahlliste_ok() throws Exception {
        Gremium g = gremiumRepository.save(new Gremium("Gremium A"));

        String body = """
            { "name": "Liste A", "gremiumId": %d }
        """.formatted(g.getId());

        mockMvc.perform(post("/api/admin/wahllisten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Liste A"));
    }

    @Test
    void createWahlliste_error_returns5xx() throws Exception {
        String body = """
            { "name": "", "gremiumId": 9999 }
        """;

        mockMvc.perform(post("/api/admin/wahllisten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void validateWahlliste_error_returns5xx() throws Exception {
        Gremium g = gremiumRepository.save(new Gremium("Gremium A"));
        Wahlliste w = wahllisteRepository.save(new Wahlliste("Liste A", g));

        mockMvc.perform(post("/api/admin/wahllisten/" + w.getId() + "/validate"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void deleteWahlliste_ok() throws Exception {
        Gremium g = gremiumRepository.save(new Gremium("Gremium A"));
        Wahlliste w = wahllisteRepository.save(new Wahlliste("Liste A", g));

        mockMvc.perform(delete("/api/admin/wahllisten/" + w.getId()))
                .andExpect(status().isNoContent());
    }
}



