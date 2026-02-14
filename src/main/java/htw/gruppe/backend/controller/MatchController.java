package htw.gruppe.backend.controller;

import htw.gruppe.backend.record.MatchResultGremiumDto;
import htw.gruppe.backend.record.VoterAnswerDto;
import htw.gruppe.backend.service.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-Controller für die Match-Berechnung zwischen
 * Wähler-Antworten und Kandidaten-Antworten.
 *
 * <p>
 * Dieser Controller stellt den Endpoint <b>/api/match</b> bereit.
 * Das Angular-Frontend sendet eine Liste von {@link VoterAnswerDto},
 * welche die Bewertungen eines Wählers enthalten.
 * </p>
 *
 * <p>
 * Die Berechnungslogik selbst wird an den {@link MatchService}
 * delegiert. Die Rückgabe erfolgt gruppiert nach Gremien
 * als {@link MatchResultGremiumDto}.
 * </p>
 *
 * @author Eisner
 * @author Dumke
 */
@RestController
@RequestMapping("/api/match")
@CrossOrigin(origins = "http://localhost:4200")
public class MatchController {

    /**
     * Service zur Durchführung der Match-Berechnung.
     */
    private final MatchService matchService;

    /**
     * Konstruktor für Dependency Injection.
     *
     * @param matchService Service zur Berechnung der Matching-Ergebnisse
     */
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    /**
     * POST-Endpoint zur Berechnung des Matchings.
     *
     * <p>
     * Erwartetes Request-Format (vom Frontend):
     * </p>
     *
     * <pre>
     * [
     *   { "aussageId": 1, "value": 7 },
     *   { "aussageId": 2, "value": 4 }
     * ]
     * </pre>
     *
     * <p>
     * Ablauf:
     * <ol>
     *   <li>Empfängt die Antworten des Wählers.</li>
     *   <li>Übergibt die Daten an den {@link MatchService}.</li>
     *   <li>Erhält eine nach Gremien gruppierte Ergebnisliste.</li>
     *   <li>Sendet diese als JSON zurück an das Frontend.</li>
     * </ol>
     * </p>
     *
     * @param voterAnswers Liste der bewerteten Aussagen
     * @return Liste der Match-Ergebnisse gruppiert nach Gremien
     * @author Eisner
     */
    @PostMapping
    public List<MatchResultGremiumDto> calculateMatch(
            @RequestBody List<VoterAnswerDto> voterAnswers
    ) {
        return matchService.calculateMatch(voterAnswers);
    }
}

    /*
     * Der folgende Block enthält die ursprüngliche Implementierung (Legacy)
     * des Match-Controllers.
     *
     * Dieser wurde vor der Umstellung auf DTO-basierte Kommunikation
     * verwendet (alte Route: /match).
     *
     * Die Logik wurde später in den MatchService verlagert
     * und strukturell überarbeitet.
     *
     * Der Code bleibt als Referenz/Backup bestehen.
     * @author Dumke
     */

    /*
    // // package htw.gruppe.backend.controller;
    //
    //    // import htw.gruppe.backend.record.KandidatWahllisteDto;
    //    // import htw.gruppe.backend.record.MatchResult;
    //    // import htw.gruppe.backend.record.MatchResultGremium;
    //    // import htw.gruppe.backend.entity.Gremium;
    //    // import htw.gruppe.backend.entity.Kandidat;
    //    // import htw.gruppe.backend.entity.KandidatAufWahlliste;
    //    // import htw.gruppe.backend.entity.KandidatenAntwort;
    //    // import htw.gruppe.backend.repository.KandidatenAntwortRepository;
    //    // import htw.gruppe.backend.repository.KandidatenRepository;
    //    // import htw.gruppe.backend.repository.GremiumRepository;
    //    // import htw.gruppe.backend.repository.KandidatAufWahllisteRepository;
    //
    //    // import org.springframework.web.bind.annotation.*;
    //    // import java.util.*;
    //    // import java.util.stream.Collectors;
    //
    //    // @CrossOrigin(origins = "http://localhost:4200")
    //    // @RestController
    //    // @RequestMapping("/match")
    //    // public class MatchControllerLegacy {
    //
    //    //     private final KandidatenRepository kandidatenRepository;
    //    //     private final KandidatenAntwortRepository kandidatenAntwortRepository;
    //    //     private final MatchService matchService;
    //    //     private final KandidatAufWahllisteRepository kandidatAufWahllisteRepository;
    //    //     private final GremiumRepository gremiumRepository;
    //
    //    //     public MatchControllerLegacy(
    //    //             KandidatenRepository kandidatenRepository,
    //    //             KandidatenAntwortRepository kandidatenAntwortRepository,
    //    //             KandidatAufWahllisteRepository kandidatAufWahllisteRepository,
    //    //             MatchService matchService,
    //    //             GremiumRepository gremiumRepository) {
    //    //         this.kandidatenRepository = kandidatenRepository;
    //    //         this.kandidatenAntwortRepository = kandidatenAntwortRepository;
    //    //         this.matchService = matchService;
    //    //         this.kandidatAufWahllisteRepository = kandidatAufWahllisteRepository;
    //    //         this.gremiumRepository = gremiumRepository;
    //    //     }
    //
    //    //     @PostMapping
    //    //     public List<MatchResultGremium> calculateMatch(@RequestBody List<Integer> voterValues) {
    //
    //    //         System.out.println("MATCH CONTROLLER START");
    //
    //    //         Map<Long, MatchResult> kandidatMatches = new HashMap<>();
    //
    //    //         List<Kandidat> kandidaten = kandidatenRepository.findAll();
    //    //         System.out.println("Kandidaten gesamt: " + kandidaten.size());
    //
    //    //         for (Kandidat k : kandidaten) {
    //
    //    //             List<KandidatenAntwort> antworten = kandidatenAntwortRepository
    //    //                     .findByKandidat_IdOrderByAussage_IdAsc(k.getId());
    //
    //    //             List<Integer> candidateValues = antworten.stream()
    //    //                     .map(KandidatenAntwort::getAnswerValue)
    //    //                     .toList();
    //
    //    //             double match = matchService.calculateTotalMatch(candidateValues, voterValues);
    //
    //    //             List<KandidatWahllisteDto> listen = k.getWahllisten().stream()
    //    //                     .map(KandidatAufWahlliste::getWahlliste)
    //    //                     .filter(Objects::nonNull)
    //    //                     .map(w -> new KandidatWahllisteDto(w.getId(), w.getName()))
    //    //                     .toList();
    //
    //    //             kandidatMatches.put(
    //    //                     k.getId(),
    //    //                     new MatchResult(
    //    //                             k.getVorname() + " " + k.getNachname(),
    //    //                             k.getId().intValue(),
    //    //                             match,
    //    //                             k.getBeschreibung(),
    //    //                             listen,
    //    //                             k.getFachbereich()));
    //    //         }
    //
    //    //         // 2) LISTENBASIERTE GREMIEN
    //    //         List<KandidatAufWahlliste> kws = kandidatAufWahllisteRepository.findAll();
    //    //         System.out.println("KAW COUNT = " + kws.size());
    //
    //    //         Map<Gremium, List<MatchResult>> listenGremien = kws.stream()
    //    //                 .filter(kaw -> kaw.getWahlliste() != null)
    //    //                 .filter(kaw -> kaw.getWahlliste().getGremium() != null)
    //    //                 .collect(Collectors.groupingBy(
    //    //                         kaw -> kaw.getWahlliste().getGremium(),
    //    //                         Collectors.mapping(
    //    //                                 kaw -> kandidatMatches.get(kaw.getKandidat().getId()),
    //    //                                 Collectors.filtering(Objects::nonNull, Collectors.toList())
    //    //                         )));
    //
    //    //         List<MatchResultGremium> result = new ArrayList<>();
    //
    //    //         listenGremien.forEach((gremium, kandidatenImGremium) -> {
    //
    //    //             kandidatenImGremium.sort(Comparator.comparingDouble(MatchResult::match).reversed());
    //
    //    //             result.add(new MatchResultGremium(
    //    //                     gremium.getId(),
    //    //                     gremium.getName(),
    //    //                     false,
    //    //                     kandidatenImGremium));
    //    //         });
    //
    //    //         // 3) FACHBEREICHS-GREMIEN
    //    //         List<Gremium> fachbereichsGremien = gremiumRepository.findByRequiresFachbereichTrue();
    //
    //    //         for (Gremium gremium : fachbereichsGremien) {
    //
    //    //             List<MatchResult> kandidatenImGremium = kandidaten.stream()
    //    //                     .filter(k -> k.getFachbereich() != null)
    //    //                     .map(k -> kandidatMatches.get(k.getId()))
    //    //                     .filter(Objects::nonNull)
    //    //                     .sorted(Comparator.comparingDouble(MatchResult::match).reversed())
    //    //                     .toList();
    //
    //    //             result.add(new MatchResultGremium(
    //    //                     gremium.getId(),
    //    //                     gremium.getName(),
    //    //                     true,
    //    //                     kandidatenImGremium));
    //    //         }
    //
    //    //         return result;
    //    //     }
    //    // }
    //    */
    //}

