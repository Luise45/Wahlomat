package htw.gruppe.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JWT-Filter für die Authentifizierung.
 * Prüft bei jeder Anfrage, ob ein JWT im Authorization-Header vorhanden ist.
 * Ist das Token gültig, wird der Benutzer als eingeloggt gesetzt.
 */
    @Component
    public class JwtUtilFilter extends OncePerRequestFilter {

        private final JwtUtil jwtUtil;

        /**
         * Erstellt den JWT-Filter mit der benötigten JwtUtil-Instanz.
         *
         * @param jwtUtil Hilfsklasse zum Auslesen und Validieren von JWTs
         */
        public JwtUtilFilter(JwtUtil jwtUtil) {
            this.jwtUtil = jwtUtil;
        }

    /**
     * Führt die JWT-Prüfung für eingehende Requests durch.
     *
     * <p>Öffentliche Admin-Endpunkte werden übersprungen.
     * Falls kein oder ein ungültiger Authorization-Header vorhanden ist,
     * wird die Anfrage ohne Authentifizierung weitergeleitet.</p>
     *
     * @param request  HTTP-Request
     * @param response HTTP-Response
     * @param filterChain Filterkette von Spring Security
     * @throws ServletException bei Servlet-Fehlern
     * @throws IOException bei I/O-Fehlern
     */
        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain)
                throws ServletException, IOException {

            

            // Authorization Header auslesen
            String authHeader = request.getHeader("Authorization");

            // Wenn kein Header oder nicht mit "Bearer " → einfach weitermachen
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Token aus dem Header ausschneiden (ab Zeichen 7, nach "Bearer ")
            String token = authHeader.substring(7);

            // Matrikelnummer (Username) aus dem Token holen
            String username = jwtUtil.extractUsername(token);

            // Nur weiter machen, wenn noch niemand im SecurityContext eingeloggt ist
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Token prüfen (Gültigkeit + Ablaufdatum)
                if (jwtUtil.validateToken(token, username)) {
              
              String role = jwtUtil.extractRole(token);

                    // Authentifizierung-Objekt bauen (ohne Rollen → leere Liste)
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    List.of(new SimpleGrantedAuthority("ROLE_" + role))
                            );

                    // User im SecurityContext als eingeloggt markieren
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Filterkette fortsetzen
            filterChain.doFilter(request, response);
        }
    }   