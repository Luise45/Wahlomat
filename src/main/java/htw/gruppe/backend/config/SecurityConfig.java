package htw.gruppe.backend.config;

import htw.gruppe.backend.security.JwtUtilFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * SecurityConfig für unser Backend.
 * Wir nutzen JWT, daher ist alles stateless (keine Session). Der JwtUtilFilter
 * prüft das Token und setzt den User in den SecurityContext.
 * Hier legen wir fest, welche Endpoints öffentlich sind und welche ein Token brauchen.
 *
 * @author Karsli
 * @author Tabatt
 * @author Eisner
 * @author Dumke
 * @author Erdogan
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtilFilter jwtUtilFilter;

    public SecurityConfig(JwtUtilFilter jwtUtilFilter) {
        this.jwtUtilFilter = jwtUtilFilter;
    }

    /**
     * Passwort-Hashing (BCrypt). Wird z.B. beim Login/Registrieren gebraucht.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Haupt-Security-Setup:
     * - CSRF aus (weil JWT + stateless)
     * - CORS an (Frontend darf auf Backend zugreifen)
     * - manche Routen sind frei (Login, Registrierung, Swagger)
     * - alles andere nur mit gültigem JWT
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // nutzt dein CorsConfigurationSource Bean unten

                .authorizeHttpRequests(auth -> auth

                        // Admin 
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // Registrierung / Login / Passwort reset
                        .requestMatchers("/api/start-registration").permitAll()
                        .requestMatchers("/api/complete-registration").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/forgot-password").permitAll()
                        .requestMatchers("/api/reset-password").permitAll()

                        // WICHTIG: Wahlomat / Wähler-Flow muss OHNE Login funktionieren
                        .requestMatchers("/api/match/**").permitAll()
                        .requestMatchers("/api/aussage/**").permitAll()
                        .requestMatchers("/api/gremien/**").permitAll()

                        // Kandidaten (öffentlich zum Anzeigen)
                        .requestMatchers("/api/kandidaten/**").permitAll()

                        // Kandidaten Antworten (bei euch aktuell öffentlich)
                        .requestMatchers("/api/kandidaten_antworten/**").permitAll()

                        // Match (alte Route ohne /api) – falls noch genutzt
                        .requestMatchers("/match/**").permitAll()

                        // Profil: soll AUTH sein
                        .requestMatchers("/api/profil/**").authenticated()

                        // Swagger
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()

                        // Alles andere braucht Token
                        .anyRequest().authenticated()
                )

                // JWT = stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // JWT Filter vor UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtUtilFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * CORS fürs Angular-Frontend (lokal).
     * Erlaubt Requests von http://localhost:4200 inkl. Headers.
     */
    @Bean
    public CorsConfigurationSource corsConfiguration1() {
        CorsConfiguration configurations = new CorsConfiguration();
        configurations.setAllowedOrigins(List.of("http://localhost:4200"));
        configurations.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Alle Arten von headers sind erlaubt.
        configurations.setAllowedHeaders(List.of("*"));
        configurations.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource paths = new UrlBasedCorsConfigurationSource();
        paths.registerCorsConfiguration("/**", configurations);
        return paths;
    }
}