package htw.gruppe.backend.controller;

import htw.gruppe.backend.controller.admin.AdminDashboardController;
import htw.gruppe.backend.record.DashboardSummaryDto;
import htw.gruppe.backend.service.AdminDashboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminDashboardController.class)
class AdminDashboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminDashboardService service;

    @Test
    void shouldReturnDashboardSummary() throws Exception {


        DashboardSummaryDto mockDto = new DashboardSummaryDto();
        mockDto.kandidaten = 5;
        mockDto.gremien = 3;
        mockDto.wahllisten = 2;
        mockDto.zuordnungen = 4;
        mockDto.aussagen = 6;

        when(service.getSummary()).thenReturn(mockDto);



        mockMvc.perform(get("/api/admin/dashboard/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kandidaten").value(5))
                .andExpect(jsonPath("$.gremien").value(3))
                .andExpect(jsonPath("$.wahllisten").value(2))
                .andExpect(jsonPath("$.zuordnungen").value(4))
                .andExpect(jsonPath("$.aussagen").value(6));
    }
}


