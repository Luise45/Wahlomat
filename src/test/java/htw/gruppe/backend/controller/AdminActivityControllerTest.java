package htw.gruppe.backend.controller;

import htw.gruppe.backend.controller.admin.AdminActivityController;
import htw.gruppe.backend.record.AdminActivityDto;
import htw.gruppe.backend.service.AdminActivityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminActivityController.class)
class AdminActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminActivityService service;

    @Test
    void shouldReturnAllActivities() throws Exception {


        AdminActivityDto a1 = new AdminActivityDto(
                "Kandidat erstellt",
                Instant.now()
        );

        AdminActivityDto a2 = new AdminActivityDto(
                "Wahlliste validiert",
                Instant.now()
        );

        when(service.getAll()).thenReturn(List.of(a1, a2));

        // WHEN & THEN
        mockMvc.perform(get("/api/admin/activity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].description").value("Kandidat erstellt"))
                .andExpect(jsonPath("$[1].description").value("Wahlliste validiert"));
    }
}
