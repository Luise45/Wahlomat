package htw.gruppe.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import htw.gruppe.backend.record.AuthResponse;
import htw.gruppe.backend.record.LoginRequest;
import htw.gruppe.backend.security.JwtUtil;
import htw.gruppe.backend.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = AuthController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
        }
)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Service wird gemockt
    @MockitoBean
    private AuthService authService;

    // nötig wegen Security / Jwt
    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    void login_ok() throws Exception {
        LoginRequest request = new LoginRequest("s059804", "secret");
        AuthResponse response =
                new AuthResponse("token123", "s059804", "Login erfolgreich");

        Mockito.when(authService.login(any(LoginRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is("token123")))
                .andExpect(jsonPath("$.matrikelnummer", is("s059804")))
                .andExpect(jsonPath("$.message", is("Login erfolgreich")));
    }

    @Test
    void login_falscheDaten() throws Exception {
        LoginRequest request = new LoginRequest("s059804", "falsch");

        Mockito.when(authService.login(any(LoginRequest.class)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.token", nullValue()))
                .andExpect(jsonPath("$.matrikelnummer", nullValue()))
                .andExpect(jsonPath("$.message", is("Fehlermeldung")));
    }

    @Test
    void login_leereFelder() throws Exception {
        String body = """
                { "matrikelnummer": "", "password": "" }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        // Service darf hier nicht aufgerufen werden
        Mockito.verifyNoInteractions(authService);
    }

    @Test
    void login_keinBody() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(authService);
    }
}
