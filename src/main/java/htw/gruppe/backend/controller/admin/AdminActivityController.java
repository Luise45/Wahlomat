package htw.gruppe.backend.controller.admin;

import htw.gruppe.backend.record.AdminActivityDto;
import htw.gruppe.backend.service.AdminActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-Controller für den Admin-Aktivitätsverlauf.
 * Ermöglicht dem Admin, seine Aktionen einzusehen.
 *
 * @author Nguemezi
 */

@RestController
@RequestMapping("/api/admin/activity")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Admin Activity", description = "Aktivitätsverlauf des Admins")
public class AdminActivityController {

    private final AdminActivityService service;

    public AdminActivityController(AdminActivityService service) {

        this.service = service;
    }

    /**
     * Liefert den Aktivitätsverlauf des Admins.
     *
     * @return Liste der Admin-Aktivitäten
     */
    @GetMapping
    @Operation(
            summary = "Admin-Aktivitätsverlauf abrufen",
            description = "Gibt alle vom Admin durchgeführten Aktionen zurück, sortiert nach Zeitpunkt."
    )
    public List<AdminActivityDto> getAllActivities() {

        return service.getAll();
    }
}
