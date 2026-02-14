/*
 * ============================================================================
 *  ALT / NICHT MEHR AKTIV
 * ============================================================================
 *
 * Diese Service-Klasse war ein früher Ansatz für die Ergebnisseite (Wähler).
 * Dabei wurden Matching-Ergebnisse direkt anhand von Gremien, Wahllisten
 * und Kandidaten aufgebaut.
 *
 * Die Logik wird im aktuellen Projektstand nicht mehr verwendet.
 * Das Matching erfolgt inzwischen über eine überarbeitete Architektur
 * (z.B. über einen zentralen MatchService und neue DTO-Strukturen).
 *
 * Der Code bleibt aus Dokumentations- und Vergleichszwecken im Repository,
 * wird jedoch nicht kompiliert oder ausgeführt.
 *
 * @author Tabatt
 * @author Dumke
 * @author Eisner
 * @version 1.1
 */

 /*
package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.Gremium;
import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.record.*;
import htw.gruppe.backend.repository.ErgebnisseiteRepository;
import htw.gruppe.backend.repository.KandidatenAntwortRepository;
import htw.gruppe.backend.repository.KandidatenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class ErgebnisseiteService {

        private final ErgebnisseiteRepository ergebnisseiteRepository;
        private final KandidatenRepository kandidatenRepository;
        private final KandidatenAntwortRepository kandidatenAntwortRepository;
        private final MatchService matchService;

        public ErgebnisseiteService(
                        ErgebnisseiteRepository ergebnisseiteRepository,
                        KandidatenRepository kandidatenRepository,
                        KandidatenAntwortRepository kandidatenAntwortRepository,
                        MatchService matchService) {
                this.ergebnisseiteRepository = ergebnisseiteRepository;
                this.kandidatenRepository = kandidatenRepository;
                this.kandidatenAntwortRepository = kandidatenAntwortRepository;
                this.matchService = matchService;
        }

        /**
         * Zentrale Methode für die Ergebnisseite.
         * Berechnet für alle Kandidaten Matching-Werte und
         * gruppiert sie nach Gremien.
         */

/*
@Transactional
public List<MatchResultGremium> findAllMatching(List<Integer> voterValues, String fachbereich) {

    List<Gremium> gremien = ergebnisseiteRepository.findAllWithRelations();
    List<Kandidat> kandidaten = kandidatenRepository.findAll();

    Map<Long, Double> scores = matchService.calculateScoresForAllCandidates(
            kandidaten,
            voterValues,
            kandidatenAntwortRepository);

    return gremien.stream()
            .map(gremium -> {
                if (gremium.isRequiresFachbereich()) {
                    return buildFachbereichsErgebnis(gremium, scores);
                } else {
                    return buildListenErgebnis(gremium, scores);
                }
            })
            .toList();
}

/**
 * LISTENBASIERTE GREMIEN
 * (z.B. Akademischer Senat)
 */
/*
private MatchResultGremium buildListenErgebnis(
        Gremium gremium,
        Map<Long, Double> scores) {

    List<MatchResult> kandidatenErgebnis = gremium.getWahllisten().stream()
            .flatMap(w -> w.getKandidaten().stream())
            .map(kaw -> {
                Kandidat kandidat = kaw.getKandidat();

                return new MatchResult(
                        kandidat.getVorname() + " " + kandidat.getNachname(),
                        kandidat.getId().intValue(),
                        scores.getOrDefault(kandidat.getId(), 0.0),
                        kandidat.getBeschreibung(),
                        List.of(),
                        kandidat.getFachbereich());
            })
            .sorted(Comparator.comparingDouble(MatchResult::match).reversed())
            .toList();

    return new MatchResultGremium(
            gremium.getId(),
            gremium.getName(),
            false,
            kandidatenErgebnis);
}

/**
 * FACHBEREICHSBASIERTE GREMIEN
 * (z.B. Fachbereichsrat, Fachschaftsrat)
 *//*
private MatchResultGremium buildFachbereichsErgebnis(
        Gremium gremium,
        Map<Long, Double> scores) {

    List<MatchResult> kandidatenErgebnis = kandidatenRepository.findAll().stream()
            .filter(k -> k.getFachbereich() != null)
            .map(kandidat -> new MatchResult(
                    kandidat.getVorname() + " " + kandidat.getNachname(),
                    kandidat.getId().intValue(),
                    scores.getOrDefault(kandidat.getId(), 0.0),
                    kandidat.getBeschreibung(),
                    List.of(),
                    kandidat.getFachbereich()))
            .sorted(Comparator.comparingDouble(MatchResult::match).reversed())
            .toList();

    return new MatchResultGremium(
            gremium.getId(),
            gremium.getName(),
            true,
            kandidatenErgebnis);
}
}
        */