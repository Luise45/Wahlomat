package htw.gruppe.backend.controller.admin;

import htw.gruppe.backend.entity.Gremium;
import htw.gruppe.backend.repository.GremiumRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Admin-Controller zur Anzeige von Gremien.
 *
 * <p>
 * Gremien sind fest definiert und können nicht angelegt,
 * geändert oder gelöscht werden. Der Admin kann diese
 * ausschließlich einsehen.
 * </p>
 *
 * @author Nguemezi
 */
@Tag(
        name = "Admin - Gremium", description = "Administrative Endpunkte zur Anzeige von Gremien")
@RestController
@RequestMapping("/api/admin/gremien")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminGremiumController {

    private final GremiumRepository gremiumRepository;

    public AdminGremiumController(GremiumRepository gremiumRepository) {
        this.gremiumRepository = gremiumRepository;
    }

    /**
     * Gibt alle vorhandenen Gremien zurück.
     *
     * @return Liste aller Gremien
     */
    @Operation(
            summary = "Alle Gremien abrufen",
            description = "Liefert eine Liste aller vorhandenen Gremien"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Gremien erfolgreich geladen",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Gremium.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<Gremium>> getAll() {
        return ResponseEntity.ok(gremiumRepository.findAll());
    }
}

