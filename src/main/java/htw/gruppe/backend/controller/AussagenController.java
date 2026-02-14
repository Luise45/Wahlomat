/**
 * Aussagen Controller
 * Im Ausssagen Controller giebt es die Endpunkte get Aussage/get Aussage by id/get kandidaten-fragen
 *
 * @author Schmidt
 * @author Tabatt
 */

package htw.gruppe.backend.controller;

import htw.gruppe.backend.record.AussageDto;
import htw.gruppe.backend.service.AussagenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller fuer Aussagen.
 * Der Endpunkt liefert alle Aussagen die aktiv sind.
 *
 * @author Tabatt
 * @version 2
 */
@RestController
@RequestMapping("/api") //Alle Endpunkte bekommen api vorangestellt
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Aussage", description = "Aussagen Api") // für swagger
public class AussagenController {

    private final Logger log = LoggerFactory.getLogger(getClass()); //to log info/errors auf die konsole

    //Service-Ansatz (passt zu eurem Backend-Aufbau + DTOs)
    private final AussagenService aussagenService;

    public AussagenController(AussagenService aussagenService) {
        this.aussagenService = aussagenService;
    }

    // Alt/Backup
    /*
    // Diese Imports wären nötig gewesen:
    // import htw.gruppe.backend.entity.Aussage;
    // import htw.gruppe.backend.repository.AussagenRepository;

    private final AussagenRepository aussagenRepository;

    public AussagenController(AussagenRepository aussagenRepository) {
        this.aussagenRepository = aussagenRepository;
    }
    */

    // Das beschreibt für Swagger: was der Endpunkt macht & welche Response-Codes möglich sind (200, 404).
    @Operation(summary = "Aussagen laden",
            description = "Aussagen in eine Liste im frontend laden")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AussageDto.class))),
            @ApiResponse(responseCode = "404", description = "Aussagen nicht gefunden",
                    content = @Content)
    })

    /*
     * Liefert alle Aussagen, die aktiv sind (DTO)
     */
    @GetMapping("/aussage")
    public ResponseEntity<List<AussageDto>> getAktiveAussagen() {
        log.info("getAktiveAussagen()");

        var ausgabe = aussagenService.getAktiveAussagen();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ausgabe);
    }

    // Alt/Backup
    /*
    @GetMapping("/aussage")  //GET Anfrage an: /api/aussage
    public ResponseEntity<List<Aussage>> getAlleAussagen() {
        log.info("getAlleAussagen()");
        List<Aussage> result = aussagenRepository.findByAktivTrue();
        log.info("{}", result);
        return ResponseEntity.ok(result);
    }
    */
}