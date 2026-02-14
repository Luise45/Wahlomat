package htw.gruppe.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import htw.gruppe.backend.record.ProfilRequest;
import htw.gruppe.backend.record.ProfilResponse;
import htw.gruppe.backend.security.JwtUtil;
import htw.gruppe.backend.service.ProfilService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfilController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProfilControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    ProfilService profilService;

    @MockitoBean
    JwtUtil jwtUtil;


    // GET PROFIL

    @Test
    void getProfil_existiert_returns200() throws Exception {
        ProfilResponse response = new ProfilResponse(
                "Max", "Muster", "1", "Informatik", "123456", "Beschreibung"
        );

        Mockito.when(profilService.getProfilByMatrikelnummer("123456"))
                .thenReturn(Optional.of(response));

        mockMvc.perform(get("/api/profil/{matrikelnummer}", "123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vorname", is("Max")))
                .andExpect(jsonPath("$.nachname", is("Muster")))
                .andExpect(jsonPath("$.fachbereich", is("1")))
                .andExpect(jsonPath("$.studiengang", is("Informatik")))
                .andExpect(jsonPath("$.matrikelnummer", is("123456")))
                .andExpect(jsonPath("$.beschreibung", is("Beschreibung")));
    }

    @Test
    void getProfil_existiertNicht_returns404() throws Exception {
        Mockito.when(profilService.getProfilByMatrikelnummer("999999"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/profil/{matrikelnummer}", "999999"))
                .andExpect(status().isNotFound());
    }


    // UPDATE PROFIL

    @Test
    void updateProfil_existiert_returns200() throws Exception {
        ProfilRequest request = new ProfilRequest("2", "Maschinenbau", "Neue Beschreibung");
        ProfilResponse response = new ProfilResponse(
                "Max", "Muster", "2", "Maschinenbau", "123456", "Neue Beschreibung"
        );

        Mockito.when(profilService.updateProfil(Mockito.eq("123456"), Mockito.any(ProfilRequest.class)))
                .thenReturn(Optional.of(response));

        mockMvc.perform(put("/api/profil/{matrikelnummer}", "123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fachbereich", is("2")))
                .andExpect(jsonPath("$.studiengang", is("Maschinenbau")))
                .andExpect(jsonPath("$.beschreibung", is("Neue Beschreibung")));
    }

    @Test
    void updateProfil_existiertNicht_returns404() throws Exception {
        ProfilRequest request = new ProfilRequest("2", "Maschinenbau", "Neue Beschreibung");

        Mockito.when(profilService.updateProfil(Mockito.eq("123456"), Mockito.any(ProfilRequest.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/profil/{matrikelnummer}", "123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        Mockito.verify(profilService, Mockito.times(1))
                .updateProfil(Mockito.eq("123456"), Mockito.any(ProfilRequest.class));
    }

    @Test
    void updateProfil_invalidRequest_returns400() throws Exception {
        // Alle Felder leer -> Validierung schlägt fehl
        ProfilRequest invalidRequest = new ProfilRequest("", "", "");

        mockMvc.perform(put("/api/profil/{matrikelnummer}", "123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
