package htw.gruppe.backend.controller.admin;

import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.repository.KandidatenRepository;
import htw.gruppe.backend.service.AdminActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Admin-Controller zur Verwaltung von Kandidaten
 *
 * @author Nguemezi
 */
@Tag(
        name = "Admin - Kandidaten",description = "Administrative Endpunkte zur Verwaltung von Kandidaten")
@RestController
@RequestMapping("/api/admin/kandidaten")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminKandidatController {

    private final KandidatenRepository kandidatenRepository;
    private final AdminActivityService adminActivityService;

    /**
     * Konstruktor
     *
     * @param kandidatenRepository Repository für Kandidaten
     * @param adminActivityService Service zum Loggen von Admin-Aktivitäten
     */
    public AdminKandidatController(
            KandidatenRepository kandidatenRepository,
            AdminActivityService adminActivityService
    ) {
        this.kandidatenRepository = kandidatenRepository;
        this.adminActivityService = adminActivityService;
    }

    /**
     * Gibt alle Kandidaten zurück.
     *
     * @return Liste aller Kandidaten
     */
    @Operation(
            summary = "Alle Kandidaten abrufen",
            description = "Liefert eine Liste aller registrierten Kandidaten"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Kandidaten erfolgreich geladen")
    })
    @GetMapping
    public ResponseEntity<List<Kandidat>> getAllKandidaten() {
        return ResponseEntity.ok(kandidatenRepository.findAllByRole("USER"));
    }

    /**
     * Löscht einen Kandidaten anhand der ID
     *
     * @param id ID des Kandidaten
     * @return HTTP 204 bei Erfolg oder 404, falls nicht gefunden
     */
    @Operation(
            summary = "Kandidaten löschen",
            description = "Löscht einen Kandidaten anhand der ID und speichert eine Aktivität"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Kandidat erfolgreich gelöscht"),
            @ApiResponse(responseCode = "404", description = "Kandidat nicht gefunden")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKandidat(
            @Parameter(description = "ID des Kandidaten", example = "1")
            @PathVariable Long id
    ) {

        // Kandidat zuerst laden
        Kandidat kandidat = kandidatenRepository.findById(id).orElse(null);

        if (kandidat == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            kandidatenRepository.deleteById(id);

            //  Aktivität mit Details speichern
            adminActivityService.log(
                    "Kandidat entfernt: "
                            + kandidat.getVorname() + " "
                            + kandidat.getNachname()
                            + " (ID: " + kandidat.getId() + ")"
            );

            return ResponseEntity.noContent().build();
        } catch (org.springframework.dao.DataIntegrityViolationException e) {

            return ResponseEntity
                    .badRequest()
                    .body("Kandidat ist einer Wahlliste zugeordnet und kann nicht gelöscht werden.");
        }
    }
}



