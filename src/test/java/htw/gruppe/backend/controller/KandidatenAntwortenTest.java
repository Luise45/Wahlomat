package htw.gruppe.backend.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import htw.gruppe.backend.entity.Aussage;
import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.record.KandidatenAntwortenRequestDto;
import htw.gruppe.backend.repository.AussagenRepository;
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

import java.security.Principal;
import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;



@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class KandidatenAntwortenTest {


    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KandidatenRepository kandidatenRepository;
    @Autowired
    KandidatenAntwortRepository kandidatenAntwortRepository;
    @Autowired
    AussagenRepository aussagenRepository;

    @BeforeEach
    void setUp() {
        kandidatenAntwortRepository.deleteAll();
        kandidatenRepository.deleteAll();
        aussagenRepository.deleteAll();
    }

    @Test
    void getEigeneAntworten_withoutPrincipal_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/kandidaten_antworten/eigene"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createKandidatenAntwort_withValidData_shouldReturnCreatedAndResponseBody() throws Exception {
        // ---------- Arrange ----------
        String matrikelnummer = "123456";
        Principal principal = () -> matrikelnummer;

        Kandidat kandidat = new Kandidat();
        // ggf. anpassen: setMatrikelnummer(...) muss bei euch existieren
        kandidat.setMatrikelnummer(matrikelnummer);
        kandidat = kandidatenRepository.save(kandidat);

        Aussage aussage = new Aussage();
        // Wenn Aussage-ID bei euch AUTO ist: diese Zeile weglassen.
        // Wenn Aussage-ID NICHT auto ist und du sie setzen musst:
        // aussage.setId(1L);
        aussage = aussagenRepository.save(aussage);

        Long aussageId = aussage.getId(); // muss vorhanden sein nach save()

        KandidatenAntwortenRequestDto request =
                new KandidatenAntwortenRequestDto(aussageId, 7);

        // ---------- Act + Assert ----------
        mockMvc.perform(post("/api/kandidaten_antworten")
                        .principal(principal) // !!! wichtig, sonst NPE im Controller
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.aussage_id", is(aussageId.intValue())))
                .andExpect(jsonPath("$.answerValue", is(7)));
    }

    @Test
    void getKandidatenAntworten_withExistingData_shouldReturnOkAndList() throws Exception {
        // ---------- Arrange ----------
        String matrikelnummer = "123456";
        Kandidat kandidat = new Kandidat();
        kandidat.setMatrikelnummer(matrikelnummer);
        kandidat = kandidatenRepository.save(kandidat);

        Aussage aussage = new Aussage();
        aussage = aussagenRepository.save(aussage);

        // Wir erzeugen eine Antwort indirekt über den POST-Endpunkt (realistischer)
        KandidatenAntwortenRequestDto request = new KandidatenAntwortenRequestDto(aussage.getId(), 5);

        mockMvc.perform(post("/api/kandidaten_antworten")
                        .principal((Principal) () -> matrikelnummer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // ---------- Act + Assert ----------
        mockMvc.perform(get("/api/kandidaten_antworten/{kandidatId}", kandidat.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(List.class)))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].aussageId", is(aussage.getId().intValue())))
                .andExpect(jsonPath("$[0].answerValue", is(5)));
    }

    @Test
    void getEigeneAntworten_withPrincipal_shouldReturnOkAndList() throws Exception {
        // ---------- Arrange ----------
        String matrikelnummer = "123456";

        Kandidat kandidat = new Kandidat();
        kandidat.setMatrikelnummer(matrikelnummer);
        kandidat = kandidatenRepository.save(kandidat);

        Aussage aussage = new Aussage();
        aussage = aussagenRepository.save(aussage);

        KandidatenAntwortenRequestDto request = new KandidatenAntwortenRequestDto(aussage.getId(), 9);

        mockMvc.perform(post("/api/kandidaten_antworten")
                        .principal((Principal) () -> matrikelnummer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // ---------- Act + Assert ----------
        mockMvc.perform(get("/api/kandidaten_antworten/eigene")
                        .principal((Principal) () -> matrikelnummer))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].aussageId", is(aussage.getId().intValue())))
                .andExpect(jsonPath("$[0].answerValue", is(9)));
    }

    @Test
    void createKandidatenAntwort_missingBody_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/kandidaten_antworten")
                        .principal((Principal) () -> "123456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createKandidatenAntwort_wrongContentType_shouldReturnUnsupportedMediaType() throws Exception {
        mockMvc.perform(post("/api/kandidaten_antworten")
                        .principal((Principal) () -> "123456")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("hello"))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void createKandidatenAntwort_invalidJson_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/kandidaten_antworten")
                        .principal((Principal) () -> "123456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest());
    }
}
