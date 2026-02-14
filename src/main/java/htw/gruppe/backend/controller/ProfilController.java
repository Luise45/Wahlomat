/**
 * REST-Controller für die Verwaltung von Kandidatenprofilen.
 *
 * Stellt Endpunkte bereit, um:
 *
 *     Das Profil eines Kandidaten anhand der Matrikelnummer abzurufen
 *     Das Profil eines Kandidaten zu aktualisieren
 *
 *
 * Endpunkte sind unter "/api/profil" verfügbar.
 * Cross-Origin Requests von http://localhost:4200 werden erlaubt.
 *
 * Swagger-Dokumentation wird durch @Tag, @Operation und @ApiResponses unterstützt.
 *
 * @author Schmidt
 * @version 1.0
 */
package htw.gruppe.backend.controller;

import htw.gruppe.backend.record.ProfilRequest;
import htw.gruppe.backend.record.ProfilResponse;
import htw.gruppe.backend.service.ProfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/profil")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Profile", description = "Profile Kandidaten Api")
public class ProfilController {

    private final ProfilService profilService;
    private final Logger log = LoggerFactory.getLogger(ProfilController.class);

    public ProfilController(ProfilService profilService) {
        this.profilService = profilService;
    }

    /**
     * Holt das Profil eines Kandidaten anhand der Matrikelnummer.
     *
     * @param matrikelnummer Matrikelnummer des Kandidaten
     * @return ResponseEntity mit ProfilResponse und HTTP 200, oder HTTP 404 falls nicht gefunden
     */
    @Operation(summary = "Profil des Kandidaten abrufen",
            description = "Holt das Profil des Kandidaten anhand der Matrikelnummer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profil erfolgreich abgerufen",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfilResponse.class))),
            @ApiResponse(responseCode = "404", description = "Kandidat nicht gefunden")
    })
    @GetMapping("/{matrikelnummer}")
    public ResponseEntity<ProfilResponse> getProfil(@PathVariable String matrikelnummer) {
        log.info("GET Profil {}", matrikelnummer);
        return profilService.getProfilByMatrikelnummer(matrikelnummer)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /**
     * Aktualisiert bearbeitbare Felder des Kandidatenprofils.
     *
     * @param matrikelnummer Matrikelnummer des Kandidaten
     * @param request ProfilRequest mit den zu aktualisierenden Daten
     * @return ResponseEntity mit aktualisiertem ProfilResponse und HTTP 200, oder HTTP 404 falls nicht gefunden
     */

    @Operation(summary = "Profil des Kandidaten aktualisieren",
            description = "Aktualisiert bearbeitbare Felder des Kandidatenprofils")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profil erfolgreich aktualisiert",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProfilResponse.class))),
            @ApiResponse(responseCode = "404", description = "Kandidat nicht gefunden")
    })
    @PutMapping("/{matrikelnummer}")
    public ResponseEntity<ProfilResponse> updateProfil(
            @PathVariable String matrikelnummer,
            @Valid @RequestBody ProfilRequest request) {

        log.info("PUT Update Profil {}", matrikelnummer);
        return profilService.updateProfil(matrikelnummer, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
