package htw.gruppe.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.record.ForgotDto;
import htw.gruppe.backend.record.ResetDto;
import htw.gruppe.backend.repository.KandidatenRepository;
import htw.gruppe.backend.security.JwtUtil;
import htw.gruppe.backend.service.ResetService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ForgotPasswordController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
        })
@AutoConfigureMockMvc(addFilters = false)
class ForgotPasswordControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @MockitoBean JwtUtil jwtUtil;

    @MockitoBean KandidatenRepository kandidatenRepository;
    @MockitoBean ResetService resetService;

    @Test
    void forgotPassword_matrikelnummerFehlt_returns400() throws Exception {
        // matrikelnummer leer -> Controller gibt badRequest zurück
        ForgotDto req = new ForgotDto("");

        mockMvc.perform(post("/api/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Matrikelnummer fehlt"));

        // Service soll dann NICHT laufen
        Mockito.verifyNoInteractions(resetService);
        Mockito.verifyNoInteractions(kandidatenRepository);
    }

    @Test
    void forgotPassword_kandidatExistiert_returns200_andSendsMail() throws Exception {
        ForgotDto req = new ForgotDto("s059804");

        // Kandidat existiert
        Mockito.when(kandidatenRepository.findByMatrikelnummer("s059804"))
                .thenReturn(Optional.of(new Kandidat()));

        // Token, den wir "fake" zurückgeben
        Mockito.when(resetService.ResetTokenErstellen("s059804"))
                .thenReturn("token-123");

        mockMvc.perform(post("/api/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().string("Falls der den Account giebt, wurde eien Email geschikt"));

        // wichtig: reset token + sendEmail sollen aufgerufen werden
        Mockito.verify(resetService).sendEmail(
                Mockito.eq("s059804@htw-berlin.de"),
                Mockito.eq("Passwort zurücksetzen"),
                Mockito.contains("http://localhost:4200/reset-password?token=token-123")
        );

    }

    @Test
    void forgotPassword_kandidatExistiertNicht_returns200_andDoesNothing() throws Exception {
        ForgotDto req = new ForgotDto("s000000");

        // Kandidat NICHT vorhanden
        Mockito.when(kandidatenRepository.findByMatrikelnummer("s000000"))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().string("Falls die Matrikelnummer exsistiert, wird die Email geschickt"));

        // darf dann keinen Token erstellen und keine Email schicken
        Mockito.verify(resetService, never()).ResetTokenErstellen(anyString());
        Mockito.verify(resetService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void resetPassword_validRequest_returns200() throws Exception {
        ResetDto req = new ResetDto("token-abc", "NeuesPasswort123");

        mockMvc.perform(post("/api/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Passwort updated")));

        Mockito.verify(resetService).resetPassword("token-abc", "NeuesPasswort123");
    }
}
