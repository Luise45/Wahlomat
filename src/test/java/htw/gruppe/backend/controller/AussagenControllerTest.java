package htw.gruppe.backend.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import htw.gruppe.backend.entity.Aussage;
import htw.gruppe.backend.repository.AussagenRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class AussagenControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AussagenRepository aussagenRepository;

    @BeforeEach
    void setUp() {
        aussagenRepository.deleteAll();
    }

    @Test
    void getAktiveAussagen_ok() throws Exception {
        
        aussagenRepository.save(new Aussage(null, "Aktiv", true));
        aussagenRepository.save(new Aussage(null, "Inaktiv", false));

        mockMvc.perform(get("/api/aussage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
                
    }

    @Test
    void getAktiveAussagen_empty_ok() throws Exception {
        mockMvc.perform(get("/api/aussage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
