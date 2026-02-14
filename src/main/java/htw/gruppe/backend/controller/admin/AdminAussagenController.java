package htw.gruppe.backend.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.*;


import htw.gruppe.backend.entity.Aussage;
import htw.gruppe.backend.repository.AussagenRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST-Controller für administrative Funktionen zur Verwaltung von Aussagen.
 * <p>
 * Dieser Controller stellt Endpunkte bereit, mit denen Administratoren
 * Aussagen abrufen, neue Aussagen erstellen und den Aktiv-Status einzelner
 * Aussagen ändern können.
 * </p>
 */


@Tag(name = "Admin - Aussagen",
description = "Administrative Endpunkte zur Verwaltung von Aussagen"
)
    /**
     * Administrative Endpunkte zur Verwaltung von Aussagen.
     *
     * @author Erdogan
     */

@RestController
@RequestMapping("/api/admin/aussagen")
public class AdminAussagenController {
    private final AussagenRepository aussagenRepository;

    /**
     * Konstruktor für den {@link AdminAussagenController}.
     *
     * @param aussagenRepository Repository für den Zugriff auf Aussagen in der Datenbank
     */


    public AdminAussagenController(AussagenRepository aussagenRepository) {
        this.aussagenRepository = aussagenRepository;
    }

    /**
     * Liefert alle vorhandenen Aussagen.
     * <p>
     * Dieser Endpunkt wird für die administrative Übersicht der Aussagen verwendet.
     * </p>
     *
     * @return ResponseEntity mit einer Liste aller Aussagen und HTTP-Status 200 (OK)
     */


@Operation(
        summary = "Alle Aussagen abrufen",
        description = "Liefert alle Aussagen zur administrativen Verwaltung"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Aussagen erfolgreich geladen")
    })

    @GetMapping
    public ResponseEntity<List<Aussage>> getAllAussagen() {
        return ResponseEntity.ok(aussagenRepository.findAll());
    }

    /**
     * Erstellt eine neue Aussage.
     *
     * @param aussage Die zu speichernde Aussage
     * @return ResponseEntity mit der gespeicherten Aussage und HTTP-Status 201 (CREATED)
     */

@Operation(
        summary = "Aussage erstellen",
        description = "Erstellt eine neue Aussage"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Aussage erfolgreich erstellt")
    })

    @PostMapping
    public ResponseEntity<Aussage> createAussage(@RequestBody Aussage aussage) {
       

         return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(aussagenRepository.save(aussage));
    }

    /**
     * Aktiviert oder deaktiviert eine Aussage anhand ihrer ID.
     *
     * @param id    Die ID der Aussage, deren Status geändert werden soll
     * @param aktiv Neuer Aktiv-Status der Aussage (true = aktiv, false = inaktiv)
     * @return ResponseEntity mit der aktualisierten Aussage oder 404 (NOT FOUND),
     *         falls keine Aussage mit der angegebenen ID existiert
     */

@Operation(
        summary = "Aussage aktivieren oder deaktivieren",
        description = "Setzt den Aktiv-Status einer Aussage"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status der Aussage erfolgreich geändert"),
        @ApiResponse(responseCode = "404", description = "Aussage nicht gefunden")
    })

    @PutMapping("/{id}/aktiv")
    public ResponseEntity<Aussage> setAktiv(
        @Parameter(description = "ID der Aussage", example = "1")
            @PathVariable Long id,


           @Parameter(description = "Aktiv-Status der Aussage", example = "true") 
            @RequestParam boolean aktiv
    ) {
        return aussagenRepository.findById(id)
                .map(aussage -> {
                    aussage.setAktiv(aktiv);
                    return ResponseEntity.ok(aussagenRepository.save(aussage));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}