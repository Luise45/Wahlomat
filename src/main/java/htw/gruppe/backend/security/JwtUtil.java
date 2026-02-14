package htw.gruppe.backend.security;

import htw.gruppe.backend.entity.Kandidat;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility-Klasse für die Erstellung, Validierung und Auswertung von JWT-Token.
 *
 * Diese Klasse unterstützt die Authentifizierung von Kandidaten anhand von
 * JWTs. Sie erlaubt das Generieren von Tokens, Extrahieren von Claims und
 * Überprüfen der Gültigkeit (inklusive Ablaufprüfung).
 *
 *
 * @author Karsli
 * @author Schmidt
 * @author Bektas
 * @author Erdogan
 * @version 1.0
 */

@Component
public class JwtUtil {

    /**
     * Geheimer Schlüssel zur Signierung der JWTs.
     */
    private static final String SECRET = "eO5AE8UBVdHJ7uJu7Z4dP7mJtwrc7glNQ4wNZyZM9IA=";

    /**
     * Gültigkeitsdauer eines Login-Tokens (30 Minuten).
     */
    private static final long EXPIRATION_TIME = 30 * 60 * 1000; // 30 Minuten

    /**
     * Erzeugt aus dem Secret einen kryptografischen Signierschlüssel
     * für das HMAC-SHA256-Verfahren.
     *
     * @return Signierschlüssel
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

/**
 * Generiert ein JWT für einen Benutzer inkl. Rollen-Claim.
 *
 * @param username Benutzername (z. B. Matrikelnummer)
 * @param role Rolle des Benutzers (z. B. "ADMIN" oder "USER")
 * @return signiertes JWT
 */
     public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role); 
        return createToken(claims, username);
    }

    /**
     * Generiert ein JWT für einen Kandidaten.
     *
     * Zusätzlich zur Matrikelnummer wird die Kandidaten-ID
     * als Claim im Token gespeichert.
     *
     * @param kandidat Kandidat, für den das Token erstellt wird
     * @return generiertes JWT
     */
    public String generateToken(Kandidat kandidat) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", kandidat.getId());   //  Kandidaten-ID ins Token

        return createToken(claims, kandidat.getMatrikelnummer());
    }

    /**
     * Erstellt ein JWT mit den angegebenen Claims und dem Subject.
     *
     * @param claims  zusätzliche Claims für das Token
     * @param subject Subject des Tokens (Benutzername)
     * @return signiertes JWT
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrahiert den Benutzernamen (Subject) aus einem JWT.
     *
     * @param token JWT
     * @return Benutzername oder {@code null}, falls das Token ungültig ist
     */
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims != null ? claims.getSubject() : null;
    }
    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return claims != null ? (String) claims.get("role") : null;
    }
    /**
     * Extrahiert alle Claims aus einem JWT.
     *
     * Ungültige, fehlerhafte oder falsch formatierte Tokens
     * werden abgefangen und führen zu {@code null}.
     * Abgelaufene Tokens liefern weiterhin ihre Claims zurück,
     * sodass eine explizite Ablaufprüfung erfolgen kann.
     *
     * @param token JWT
     * @return Claims oder {@code null}, falls ungültig
     */
    private Claims extractAllClaims(String token) {
        try {
            if (token == null || token.isEmpty() || token.chars().filter(ch -> ch == '.').count() != 2) {
                return null;
            }
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // Token abgelaufen
            return e.getClaims();
        } catch (Exception e) {
            // Ungültiges Token
            return null;
        }
    }


    /**
     * Prüft, ob ein Token für einen bestimmten Benutzer gültig ist.
     *
     * Das Token ist gültig, wenn:
     * <ul>
     *   <li>der Benutzername mit dem Token übereinstimmt</li>
     *   <li>das Token nicht abgelaufen ist</li>
     * </ul>
     *
     * @param token    JWT
     * @param username erwarteter Benutzername
     * @return {@code true}, wenn das Token gültig ist
     */
    public boolean validateToken(String token, String username) {
            String extractedUsername = extractUsername(token);
            return extractedUsername != null &&
                    extractedUsername.equals(username) &&
                    !isTokenExpired(token);
        }

    /**
     * Prüft, ob ein JWT abgelaufen ist.
     *
     * @param token JWT
     * @return {@code true}, wenn das Token abgelaufen oder ungültig ist
     */
    private boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims == null || claims.getExpiration().before(new Date());
    }

    /**
     * Generiert ein JWT für den Passwort-Zurücksetzen-Prozess.
     *
     * Das Token ist zeitlich begrenzt und enthält einen speziellen
     * Claim zur Unterscheidung von Login-Tokens.
     *
     * @param kandidat Kandidat, für den das Reset-Token erstellt wird
     * @return Passwort-Reset-JWT
     */
    public String generatePasswordResetToken(Kandidat kandidat) {
        long expirationMillis = 3600000; // 1 hour
        return Jwts.builder()
                .setSubject(kandidat.getMatrikelnummer())
                .claim("type", "password-reset")
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}