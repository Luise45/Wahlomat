package htw.gruppe.backend.controller;

import htw.gruppe.backend.controller.admin.AdminGremiumController;
import htw.gruppe.backend.entity.Gremium;
import htw.gruppe.backend.repository.GremiumRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminGremiumController.class)
class AdminGremiumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GremiumRepository gremiumRepository;

    @Test
    void shouldReturnAllGremien() throws Exception {


        Gremium g1 = new Gremium();
        g1.setId(1L);
        g1.setName("Studierendenparlament");

        Gremium g2 = new Gremium();
        g2.setId(2L);
        g2.setName("Fachschaft");

        when(gremiumRepository.findAll())
                .thenReturn(List.of(g1, g2));


        mockMvc.perform(get("/api/admin/gremien"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Studierendenparlament"))
                .andExpect(jsonPath("$[1].name").value("Fachschaft"));
    }
}
