package htw.gruppe.backend.controller;

import htw.gruppe.backend.record.MatchResultGremiumDto;
import htw.gruppe.backend.record.VoterAnswerDto;
import htw.gruppe.backend.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller für die Ergebnisseite.
 * Liefert die Match-Ergebnisse gruppiert nach Gremien.
 *
 * ⚠️ Nutzt den NEUEN DTO-basierten Match-Flow
 *
 * @author Dumke, Tabatt
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Ergebnisseite", description = "Ergebnisseiten API")
public class ErgebnisseiteController {

    private final MatchService matchService;

    public ErgebnisseiteController(MatchService matchService) {
        this.matchService = matchService;
    }

    /**
     * POST /api/gremien/match
     *
     * Frontend schickt:
     * [
     *   { aussageId: 1, value: 7 },
     *   { aussageId: 2, value: 3 },
     *   ...
     * ]
     *
     * Backend liefert:
     * List<MatchResultGremiumDto>
     */
    @Operation(
            summary = "Berechnet Match-Ergebnisse pro Gremium",
            description = "Ergebnisse gruppiert nach Gremien für die Ergebnisseite"
    )
    @PostMapping("/gremien/match")
    public List<MatchResultGremiumDto> calculateMatch(
            @RequestBody List<VoterAnswerDto> voterAnswers
    ) {
        return matchService.calculateMatch(voterAnswers);
    }
}