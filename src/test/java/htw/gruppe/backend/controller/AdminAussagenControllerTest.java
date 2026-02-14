package htw.gruppe.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;


import htw.gruppe.backend.entity.Aussage;
import htw.gruppe.backend.repository.AussagenRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class AdminAussagenControllerTest {

     
    @Autowired
    MockMvc mockMvc;

    
    @Autowired
    AussagenRepository aussagenRepository;

    
    @BeforeEach
    void setUp() {
        aussagenRepository.deleteAll();
    }

    
    @Test
    void getAllAussagen_ok() throws Exception {
        
        aussagenRepository.save(new Aussage(null, "Test Aussage", true));

        
        mockMvc.perform(get("/api/admin/aussagen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

   
    @Test
    void createAussage_ok() throws Exception {
       
        String body = """
            { "text": "Neue Aussage", "aktiv": true }
        """;

        
        mockMvc.perform(post("/api/admin/aussagen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("Neue Aussage"))
                .andExpect(jsonPath("$.aktiv").value(true));
    }

    
    @Test
    void setAktiv_ok() throws Exception {
        
        Aussage a = aussagenRepository.save(new Aussage(null, "Alt", false));

        
        mockMvc.perform(put("/api/admin/aussagen/" + a.getId() + "/aktiv")
                        .param("aktiv", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.aktiv").value(true));
    }

    
    @Test
    void setAktiv_notFound_returns404() throws Exception {
        mockMvc.perform(put("/api/admin/aussagen/9999/aktiv")
                        .param("aktiv", "true"))
                .andExpect(status().isNotFound());
    }
}