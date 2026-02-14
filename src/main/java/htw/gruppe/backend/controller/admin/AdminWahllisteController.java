package htw.gruppe.backend.controller.admin;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import htw.gruppe.backend.entity.Wahlliste;
import htw.gruppe.backend.service.WahllisteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Map;

/**
 * REST-Controller für administrative Funktionen zur Verwaltung von Wahllisten.
 * <p>
 * Dieser Controller stellt Endpunkte bereit, mit denen Administratoren:
 * <ul>
 *   <li>alle Wahllisten abrufen können</li>
 *   <li>eine einzelne Wahlliste anhand ihrer ID laden können</li>
 *   <li>neue Wahllisten anlegen können</li>
 *   <li>Wahllisten validieren können</li>
 *   <li>Wahllisten löschen können</li>
 * </ul>
 *
 *
 * @author Erdogan
 */

@RestController
@RequestMapping("/api/admin/wahllisten")
@Tag(
    name = "Admin - Wahllisten",
    description = "Administrative Endpunkte zur Verwaltung und Validierung von Wahllisten"
)

public class AdminWahllisteController {

    /**
     * Service für die Geschäftslogik rund um Wahllisten.
     */

   private final WahllisteService wahllisteService;

    /**
     * Konstruktor zur Übergabe des WahllisteService.
     *
     * @param wahllisteService Service für die Verwaltung von Wahllisten
     */

    public AdminWahllisteController(WahllisteService wahllisteService) {
        this.wahllisteService = wahllisteService;
    }

    /**
     * Liefert alle vorhandenen Wahllisten.
     *
     * @return ResponseEntity mit einer Liste aller Wahllisten und HTTP-Status 200 (OK)
     */

@Operation(
        summary = "Alle Wahllisten abrufen",
        description = "Liefert alle vorhandenen Wahllisten inklusive ihres aktuellen Validierungsstatus"
    )
   @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Wahllisten erfolgreich geladen",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Wahlliste.class)
            )
        )
    })

    @GetMapping
    public ResponseEntity<List<Wahlliste>> getAllWahllisten() {
        return ResponseEntity.ok(wahllisteService.getAllWahllisten());
    }

    /**
     * Liefert eine einzelne Wahlliste anhand ihrer ID.
     *
     * @param wahllisteId ID der Wahlliste
     * @return ResponseEntity mit der gefundenen Wahlliste oder HTTP-Status 404, falls keine Wahlliste existiert
     */

@Operation(

        summary = "Wahlliste anhand der ID abrufen",
        description = "Liefert eine einzelne Wahlliste inklusive zugehöriger Kandidaten und Validierungsstatus"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Wahlliste erfolgreich geladen",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Wahlliste.class)
            )
        ),
    @ApiResponse(
            responseCode = "404",
            description = "Wahlliste nicht gefunden"
        )
    })

    @GetMapping("/{wahllisteId}")
    public ResponseEntity<Wahlliste> getWahlliste(
            @Parameter(
            description = "ID der Wahlliste",
            example = "1"
        )
            @PathVariable Long wahllisteId
    ) {
        return ResponseEntity.ok(wahllisteService.getWahlliste(wahllisteId));
    }

    /**
     * Legt eine neue Wahlliste für ein bestimmtes Gremium an.
     *
     * @param body Request-Body mit den Feldern {@code name} und {@code gremiumId}
     * @return ResponseEntity mit der neu erstellten Wahlliste und HTTP-Status 201 (Created)
     */

@Operation(
        summary = "Neue Wahlliste anlegen",
        description = "Legt eine neue Wahlliste für ein bestimmtes Gremium an"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Wahlliste erfolgreich angelegt"),
        @ApiResponse(responseCode = "400", description = "Ungültige Eingabedaten"),
        @ApiResponse(responseCode = "404", description = "Gremium nicht gefunden")
    })
    @PostMapping
    public ResponseEntity<Wahlliste> createWahlliste(
        @RequestBody Map<String, Object> body
    ) {
        String name = (String) body.get("name");
        Long gremiumId = Long.valueOf(body.get("gremiumId").toString());

        Wahlliste wahlliste = wahllisteService.createWahlliste(name, gremiumId);
        return ResponseEntity.status(201).body(wahlliste);
    }

    /**
     * Validiert eine Wahlliste.
     * <p>
     * Eine Validierung ist nur möglich, wenn die Wahlliste mindestens drei Kandidaten enthält.
     *
     * @param wahllisteId ID der zu validierenden Wahlliste
     * @return HTTP-Status 204 (No Content) bei Erfolg
     */

    @Operation(
        summary = "Wahlliste validieren",
        description = "Validiert eine Wahlliste. Eine Validierung ist nur möglich, wenn die Wahlliste mindestens drei Kandidaten enthält."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Wahlliste erfolgreich validiert"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Wahlliste enthält weniger als drei Kandidaten"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Wahlliste nicht gefunden"
        )
    })

    @PostMapping("/{wahllisteId}/validate")
    public ResponseEntity<Void> validateWahlliste(
        @Parameter(
            description = "ID der zu validierenden Wahlliste",
            example = "1"
        )
            @PathVariable Long wahllisteId
    ) {
        wahllisteService.validateWahlliste(wahllisteId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Löscht eine Wahlliste inklusive aller zugehörigen Kandidaten-Zuordnungen.
     *
     * @param wahllisteId ID der zu löschenden Wahlliste
     * @return HTTP-Status 204 (No Content) bei erfolgreichem Löschen
     */

 @Operation(
        summary = "Wahlliste löschen",
        description = "Löscht eine Wahlliste vollständig inklusive aller zugehörigen Kandidaten-Zuordnungen"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Wahlliste erfolgreich gelöscht"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Wahlliste nicht gefunden"
        )
    })
    
    @DeleteMapping("/{wahllisteId}")
    public ResponseEntity<Void> deleteWahlliste(
     @Parameter(
            description = "ID der zu löschenden Wahlliste",
            example = "1"
        )
            @PathVariable Long wahllisteId
    ) {
        wahllisteService.deleteWahlliste(wahllisteId);
        return ResponseEntity.noContent().build();
    }
}




