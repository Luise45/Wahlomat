package htw.gruppe.backend.controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import htw.gruppe.backend.record.VoterAnswerDto;
import htw.gruppe.backend.repository.ErgebnisseiteRepository;
import htw.gruppe.backend.repository.KandidatenAntwortRepository;
import htw.gruppe.backend.repository.KandidatenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ErgebnisseiteControllerTest
{
    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Autowired ErgebnisseiteRepository ergebnisseiteRepository;
    @Autowired KandidatenRepository kandidatenRepository;
    @Autowired KandidatenAntwortRepository kandidatenAntwortRepository;

    @BeforeEach
    void setUp() {
        kandidatenAntwortRepository.deleteAll();
        kandidatenRepository.deleteAll();
        ergebnisseiteRepository.deleteAll();
    }

    @Test
    void calculateMatch_withValidRequest_shouldReturnOk() throws Exception {
        // Beispiel: so wie dein Controller es erwartet
        List<VoterAnswerDto> request = List.of(
                new VoterAnswerDto(1L, 7),
                new VoterAnswerDto(2L, 3),
                new VoterAnswerDto(3L, 10),
                new VoterAnswerDto(4L, 5)
        );

        mockMvc.perform(post("/api/match")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void calculateMatch_withMissingBody_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/match")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void calculateMatch_withWrongContentType_shouldReturnUnsupportedMediaType() throws Exception {
        List<VoterAnswerDto> request = List.of(new VoterAnswerDto(1L, 7));

        mockMvc.perform(post("/api/match")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void calculateMatch_withInvalidJson_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/match")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("not-a-json"))
                .andExpect(status().isBadRequest());
    }



}
