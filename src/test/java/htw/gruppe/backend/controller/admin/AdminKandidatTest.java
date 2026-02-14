package htw.gruppe.backend.controller.admin;

import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.repository.KandidatenRepository;
import htw.gruppe.backend.service.AdminActivityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests für {@link AdminKandidatController}.
 *
 * <p>WebMvcTest testet nur den Controller-Layer. Repository und Service werden gemockt.</p>
 */
@WebMvcTest(AdminKandidatController.class)
@AutoConfigureMockMvc(addFilters = false) // Security aus, damit wir nur Controller-Logik testen
class AdminKandidatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KandidatenRepository kandidatenRepository;

    @MockBean
    private AdminActivityService adminActivityService;

    @Test
    void getAllKandidaten_returns200AndList() throws Exception {
        // Arrange
        Kandidat k1 = new Kandidat();
        ReflectionTestUtils.setField(k1, "id", 1L);
        k1.setVorname("Max");
        k1.setNachname("Muster");
        k1.setRole("USER");
        k1.setMatrikelnummer("s0123456");

        Kandidat k2 = new Kandidat();
        ReflectionTestUtils.setField(k2, "id", 2L);
        k2.setVorname("Erika");
        k2.setNachname("Mustermann");
        k2.setRole("USER");
        k2.setMatrikelnummer("s0654321");

        when(kandidatenRepository.findAllByRole("USER")).thenReturn(List.of(k1, k2));

        // Act + Assert
        mockMvc.perform(get("/api/admin/kandidaten"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].vorname").value("Max"))
                .andExpect(jsonPath("$[0].nachname").value("Muster"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].vorname").value("Erika"))
                .andExpect(jsonPath("$[1].nachname").value("Mustermann"));

        verify(kandidatenRepository).findAllByRole("USER");
        verifyNoInteractions(adminActivityService);
    }

    @Test
    void deleteKandidat_existing_returns204AndLogsActivity() throws Exception {
        // Arrange
        long id = 7L;

        Kandidat kandidat = new Kandidat();
        ReflectionTestUtils.setField(kandidat, "id", id);
        kandidat.setVorname("Nina");
        kandidat.setNachname("Eisner");
        kandidat.setRole("USER");
        kandidat.setMatrikelnummer("s0000007");

        when(kandidatenRepository.findById(id)).thenReturn(Optional.of(kandidat));
        doNothing().when(kandidatenRepository).deleteById(id);

        // Act + Assert
        mockMvc.perform(delete("/api/admin/kandidaten/{id}", id))
                .andExpect(status().isNoContent());

        verify(kandidatenRepository).findById(id);
        verify(kandidatenRepository).deleteById(id);

        verify(adminActivityService).log(argThat(msg ->
                msg.contains("Kandidat entfernt:") &&
                        msg.contains("Nina") &&
                        msg.contains("Eisner") &&
                        msg.contains("(ID: 7)")
        ));
    }

    @Test
    void deleteKandidat_notFound_returns404() throws Exception {
        // Arrange
        long id = 999L;
        when(kandidatenRepository.findById(id)).thenReturn(Optional.empty());

        // Act + Assert
        mockMvc.perform(delete("/api/admin/kandidaten/{id}", id))
                .andExpect(status().isNotFound());

        verify(kandidatenRepository).findById(id);
        verify(kandidatenRepository, never()).deleteById(anyLong());
        verifyNoInteractions(adminActivityService);
    }

    @Test
    void deleteKandidat_dataIntegrityViolation_returns400AndMessage() throws Exception {
        // Arrange
        long id = 3L;

        Kandidat kandidat = new Kandidat();
        ReflectionTestUtils.setField(kandidat, "id", id);
        kandidat.setVorname("Tom");
        kandidat.setNachname("Mueller");

        when(kandidatenRepository.findById(id)).thenReturn(Optional.of(kandidat));
        doThrow(new DataIntegrityViolationException("FK constraint"))
                .when(kandidatenRepository).deleteById(id);

        // Act + Assert
        mockMvc.perform(delete("/api/admin/kandidaten/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Kandidat ist einer Wahlliste zugeordnet")));

        verify(kandidatenRepository).findById(id);
        verify(kandidatenRepository).deleteById(id);

        verifyNoInteractions(adminActivityService);
    }
}