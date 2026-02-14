package htw.gruppe.backend.controller.admin;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import htw.gruppe.backend.entity.KandidatAufWahlliste;
import htw.gruppe.backend.service.KandidatAufWahllisteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST-Controller für administrative Funktionen zur Verwaltung von
 * Kandidaten auf Wahllisten.
 * <p>
 * Dieser Controller stellt Endpunkte bereit, mit denen Administratoren:
 * <ul>
 *   <li>alle Zuordnungen von Kandidaten zu Wahllisten abrufen können</li>
 *   <li>Kandidaten zu Wahllisten hinzufügen können</li>
 *   <li>Kandidaten von Wahllisten entfernen können</li>
 * </ul>
 *
 *
 * @author Erdogan
 */


@RestController
@RequestMapping("/api/admin/wahllisten")
@Tag(
    name = "Admin - Kandidat:in auf Wahlliste",
    description = "Administrative Endpunkte zur Verwaltung von Kandidaten auf Wahllisten"
)
public class AdminKandidatAufWahllisteController {

    /**
     * Service für die Geschäftslogik rund um Kandidaten-Wahllisten-Zuordnungen.
     */
    
    private final KandidatAufWahllisteService kandidatAufWahllisteService;
    public AdminKandidatAufWahllisteController(

    /**
     * Konstruktor für den Controller.
     *
     * @param kandidatAufWahllisteService Service für Zuordnungen zwischen Kandidaten und Wahllisten
     */
            KandidatAufWahllisteService kandidatAufWahllisteService
    ) {
        this.kandidatAufWahllisteService = kandidatAufWahllisteService;
    /**
     * Liefert alle Zuordnungen von Kandidaten zu Wahllisten.
     *
     * @return Liste aller Kandidat-Auf-Wahlliste-Zuordnungen
     */
    }
 @Operation(
        summary = "Alle Kandidat–Wahllisten-Zuordnungen abrufen",
        description = "Liefert alle Kandidaten mit ihren zugehörigen Wahllisten"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Zuordnungen erfolgreich geladen")
    })
    @GetMapping("/zuordnungen")
    public ResponseEntity<List<KandidatAufWahlliste>> getAllMappings() {
        return ResponseEntity.ok(
            kandidatAufWahllisteService.getAllMappings()
        );
    }

    /**
     * Fügt einen bestehenden Kandidaten zu einer bestehenden Wahlliste hinzu.
     *
     * @param wahllisteId ID der Wahlliste
     * @param kandidatId  ID des Kandidaten
     * @return HTTP 200, wenn die Zuordnung erfolgreich erstellt wurde
     */

@Operation(
        summary = "Kandidaten zu einer Wahlliste hinzufügen",
        description = "Fügt einen bestehenden Kandidaten einer bestehenden Wahlliste hinzu"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Kandidat erfolgreich zur Wahlliste hinzugefügt"),
        @ApiResponse(responseCode = "404", description = "Wahlliste oder Kandidat nicht gefunden")
    })
    @PostMapping("/{wahllisteId}/kandidaten/{kandidatId}")
    public ResponseEntity<Void> addKandidat(
            @Parameter(description = "ID der Wahlliste", example = "1")
            @PathVariable Long wahllisteId,

            @Parameter(description = "ID des Kandidaten", example = "10")
            @PathVariable Long kandidatId
    ) {
        kandidatAufWahllisteService.addKandidatToWahlliste(kandidatId, wahllisteId);
        return ResponseEntity.ok().build();
    }

    
    /**
     * Entfernt einen Kandidaten von einer Wahlliste.
     *
     * @param wahllisteId ID der Wahlliste
     * @param kandidatId  ID des Kandidaten
     * @return HTTP 204, wenn der Kandidat erfolgreich entfernt wurde
     */

@Operation(

        summary = "Kandidaten von einer Wahlliste entfernen",
        description = "Entfernt einen Kandidaten von einer Wahlliste"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Kandidat erfolgreich von der Wahlliste entfernt"),
        @ApiResponse(responseCode = "404", description = "Wahlliste oder Kandidat nicht gefunden")
    })

    @DeleteMapping("/{wahllisteId}/kandidaten/{kandidatId}")
    public ResponseEntity<Void> removeKandidat(
            @Parameter(description = "ID der Wahlliste", example = "1")
            @PathVariable Long wahllisteId,
            

           
            @Parameter(description = "ID des Kandidaten", example = "10")
            @PathVariable Long kandidatId
    ) {
        kandidatAufWahllisteService.removeKandidatFromWahlliste(kandidatId, wahllisteId);
        return ResponseEntity.noContent().build();
    }
}



