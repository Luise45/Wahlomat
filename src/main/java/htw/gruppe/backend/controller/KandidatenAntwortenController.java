/**
 * Controller fur Kandidaten Antworten
 *
 * @author Tabatt
 * @version 2.0
 */

package htw.gruppe.backend.controller;

import htw.gruppe.backend.record.AussageDto;
import htw.gruppe.backend.record.KandidatenAntwortenDto;
import htw.gruppe.backend.record.KandidatenAntwortenRequestDto;
import htw.gruppe.backend.record.KandidatenAntwortenResponseDto;
import htw.gruppe.backend.service.KandidatenAntwortService;
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
import java.security.Principal;
import java.util.List;

/**
 * Controller fur Kandidaten Antworten
 * @author Tabatt
 * @version 2.0
 */

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Kandidaten Antworten", description = "Kandidaten Antworten Api")// fur swagger
public class KandidatenAntwortenController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private KandidatenAntwortService kandidatenAntwortService;


    public KandidatenAntwortenController(KandidatenAntwortService kandidatenAntwortService) {
        this.kandidatenAntwortService = kandidatenAntwortService;
    }

    // für Swagger
    @Operation(summary = "Antworten der Kandidaten laden",
            description = "Die Antworten zum Fragebogen laden ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AussageDto.class))),
            @ApiResponse(responseCode = "404", description = "Antworten nicht gefunden",
                    content = @Content)})


    /**
     * Get Endpunkt fuer die Antworten eines Kandidaten
     *
     * @param kandidatId Kandidat by id
     * @return response entity
     */
    @GetMapping("/kandidaten_antworten/{kandidatId}")
    public ResponseEntity<List<KandidatenAntwortenDto>> getKandidatenAntworten(
            @PathVariable Long kandidatId) {

        logger.info("get KandidatenAntwort by id");

        var ausgabe = kandidatenAntwortService.getKandidatenAntwort(kandidatId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ausgabe);
    }


    /**
     * Danuuu
     * Gibt dem angemeldeten Kandidaten seine eigene Antworten zurück
     *
     * @param principal
     * @return
     */

    @GetMapping("/kandidaten_antworten/eigene")
    public ResponseEntity<List<KandidatenAntwortenDto>> getEigeneAntworten(Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Der name von dem Benutzer der gerade eingeloggt ist wird geholt
        String matrikelnummer = principal.getName();

        //Antworten zurück geholt, dei zu Kandiatetn gehören
        List<KandidatenAntwortenDto> antworten =
                kandidatenAntwortService.getKandidatenAntwortByBenutzer(matrikelnummer);

        // Antwort wird zurück geschickt , wenn es keine Antwort  gibt dann leer
        return ResponseEntity.ok(antworten);
    }


    /**
     * Post fuer Kandidaten Antworten
     * Principal fuer den Eingeloggten Kandidaten
     *
     * @param request   dto
     * @param principal der logged in kandidat
     * @return response entity
     */

    @PostMapping("/kandidaten_antworten")
    public ResponseEntity<KandidatenAntwortenResponseDto> createKandidatenAntwort(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request",
                    required = true,
                    content = @Content(schema = @Schema(implementation = KandidatenAntwortenRequestDto.class)))
            @RequestBody KandidatenAntwortenRequestDto request, Principal principal) {
        String benutzer = principal.getName();

        KandidatenAntwortenResponseDto savedAntwort = kandidatenAntwortService.postKandidatenAntwort(request, benutzer);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedAntwort);
    }

    /*
    @PostMapping("/kandidaten_antworten/final")
    public ResponseEntity<Void> finalAbgeben(Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String matrikelnummer = principal.getName();

        kandidatenAntwortService.finalAbgeben(matrikelnummer);

        return ResponseEntity.noContent().build();
    }
     */
}





















