package htw.gruppe.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import htw.gruppe.backend.record.MatchResultGremiumDto;
import htw.gruppe.backend.record.VoterAnswerDto;
import htw.gruppe.backend.security.JwtUtil;
import htw.gruppe.backend.service.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;


@WebMvcTest(MatchController.class)
@AutoConfigureMockMvc(addFilters = false) // falls Security nervt
class MatchControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    // nötig wegen Security / Jwt
    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean MatchService matchService;

    @Test
    void calculateMatch_returnsResultFromService() throws Exception {
        // Request vom Frontend
        List<VoterAnswerDto> request = List.of(
                new VoterAnswerDto(1L, 7),
                new VoterAnswerDto(2L, 4)
        );

        // Service-Response mocken
        List<MatchResultGremiumDto> response = List.of(
                new MatchResultGremiumDto(1L, "Fachschaft", false, List.of())
        );

        when(matchService.calculateMatch(anyList())).thenReturn(response);

        mockMvc.perform(post("/api/match")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].gremiumId").value(1))
                .andExpect(jsonPath("$[0].gremiumName").value("Fachschaft"))
                .andExpect(jsonPath("$[0].requiresFachbereich").value(false))
                .andExpect(jsonPath("$[0].kandidaten", hasSize(0)));

        verify(matchService, times(1)).calculateMatch(anyList());
    }
}
