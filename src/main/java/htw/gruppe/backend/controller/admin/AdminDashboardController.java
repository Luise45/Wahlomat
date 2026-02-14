package htw.gruppe.backend.controller.admin;

import htw.gruppe.backend.record.DashboardSummaryDto;
import htw.gruppe.backend.service.AdminDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-Controller für das Admin-Dashboard.
 *
 * <p>
 * Stellt Informationen für die Admin-Übersicht bereit,
 * zum Beispiel Kennzahlen zu Kandidaten, Gremien und Wahllisten.
 * </p>
 *
 * @author Nguemezi
 */
@Tag(
        name = "Admin Dashboard",
        description = "Endpoints für die Admin-Dashboard-Übersicht"
)
@RestController
@RequestMapping("/api/admin/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminDashboardController {

    private final AdminDashboardService service;

    /**
     * Konstruktor für den AdminDashboardController.
     *
     * @param service Service zur Ermittlung der Dashboard-Zusammenfassung
     */
    public AdminDashboardController(AdminDashboardService service) {
        this.service = service;
    }

    /**
     * Liefert eine Zusammenfassung für das Admin-Dashboard.
     *
     * @return {@link DashboardSummaryDto} mit aggregierten Kennzahlen
     */
    @Operation(
            summary = "Dashboard-Zusammenfassung abrufen",
            description = "Gibt Informationen wie die Anzahl von Kandidaten, Gremien und Wahllisten zurück."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Erfolgreich"),
            @ApiResponse(responseCode = "403", description = "Kein Zugriff")
    })
    @GetMapping("/summary")
    public DashboardSummaryDto summary() {
        return service.getSummary();
    }
}

