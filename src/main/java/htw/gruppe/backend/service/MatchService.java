package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.Gremium;
import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.entity.KandidatAufWahlliste;
import htw.gruppe.backend.entity.KandidatenAntwort;
import htw.gruppe.backend.entity.Wahlliste;
import htw.gruppe.backend.record.KandidatWahllisteDto;
import htw.gruppe.backend.record.MatchResultDto;
import htw.gruppe.backend.record.MatchResultGremiumDto;
import htw.gruppe.backend.record.VoterAnswerDto;
import htw.gruppe.backend.repository.GremiumRepository;
import htw.gruppe.backend.repository.KandidatAufWahllisteRepository;
import htw.gruppe.backend.repository.KandidatenAntwortRepository;
import htw.gruppe.backend.repository.KandidatenRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h2>MatchService</h2>
 *
 * <p>
 * Dieser Service berechnet das Matching zwischen Wähler-Antworten
 * und Kandidaten-Antworten.
 * </p>
 *
 * <p>
 * Ablauf:
 * <ol>
 *     <li>Wähler-Antworten werden in eine Map umgewandelt</li>
 *     <li>Für jeden Kandidaten wird ein Matching-Wert (0–100%) berechnet</li>
 *     <li>Kandidaten werden nach Gremien gruppiert</li>
 *     <li>Das Ergebnis wird als strukturierte DTO-Liste zurückgegeben</li>
 * </ol>
 * </p>
 *
 * <p>
 * Das Matching basiert auf der Distanz zwischen zwei Werten (1–10).
 * </p>
 *
 * @author Eisner
 * @author Dumke
 * @version 1.0
 */
@Service
public class MatchService {

    private final KandidatenRepository kandidatenRepository;
    private final KandidatenAntwortRepository kandidatenAntwortRepository;
    private final KandidatAufWahllisteRepository kandidatAufWahllisteRepository;
    private final GremiumRepository gremiumRepository;

    /**
     * Konstruktor für Dependency Injection.
     *
     * @param kandidatenRepository Repository für Kandidaten
     * @param kandidatenAntwortRepository Repository für Kandidatenantworten
     * @param kandidatAufWahllisteRepository Verknüpfung Kandidat ↔ Wahlliste
     * @param gremiumRepository Repository für Gremien
     */
    public MatchService(
            KandidatenRepository kandidatenRepository,
            KandidatenAntwortRepository kandidatenAntwortRepository,
            KandidatAufWahllisteRepository kandidatAufWahllisteRepository,
            GremiumRepository gremiumRepository
    ) {
        this.kandidatenRepository = kandidatenRepository;
        this.kandidatenAntwortRepository = kandidatenAntwortRepository;
        this.kandidatAufWahllisteRepository = kandidatAufWahllisteRepository;
        this.gremiumRepository = gremiumRepository;
    }

    /**
     * Zentrale Matching-Methode.
     *
     * <p>
     * Berechnet für alle Kandidaten das Matching mit den übergebenen
     * Wähler-Antworten und gruppiert die Ergebnisse nach Gremien.
     * </p>
     *
     * @param voterAnswers Liste der Antworten des Wählers
     * @return Liste von MatchResultGremiumDto, sortiert nach Gremienname
     */
    public List<MatchResultGremiumDto> calculateMatch(List<VoterAnswerDto> voterAnswers) {

        /**
         * Schritt 1:
         * Wähler-Antworten in eine Map umwandeln:
         * aussageId -> value
         */
        Map<Long, Integer> voterMap = voterAnswers.stream()
                .collect(Collectors.toMap(
                        VoterAnswerDto::aussageId,
                        VoterAnswerDto::value,
                        (a, b) -> b
                ));

        List<Kandidat> kandidaten = kandidatenRepository.findAll();

        /**
         * Interne Hilfsstruktur:
         * Speichert Kandidat + berechnetes Match
         */
        record KandidatMatch(Kandidat kandidat, double match) {}

        List<KandidatMatch> kandidatMatches = new ArrayList<>();

        /**
         * Schritt 2:
         * Für jeden Kandidaten Matching berechnen
         */
        for (Kandidat kandidat : kandidaten) {

            List<KandidatenAntwort> antworten =
                    kandidatenAntwortRepository.findByKandidat_IdOrderByAussage_IdAsc(kandidat.getId());

            if (antworten == null || antworten.isEmpty()) continue;

            double sum = 0.0;
            int count = 0;

            for (KandidatenAntwort ka : antworten) {

                if (ka == null || ka.getAussage() == null || ka.getAussage().getId() == null)
                    continue;

                Long aussageId = ka.getAussage().getId();
                Integer voterValue = voterMap.get(aussageId);
                Integer candidateValue = ka.getAnswerValue();

                if (voterValue == null || candidateValue == null)
                    continue;

                sum += calculateMatchSingle(candidateValue, voterValue);
                count++;
            }

            if (count == 0) continue;

            double match = Math.round(sum / count);
            kandidatMatches.add(new KandidatMatch(kandidat, match));
        }

        if (kandidatMatches.isEmpty()) {
            return List.of();
        }

        /**
         * Schritt 3:
         * Kandidaten nach Gremien und Wahllisten gruppieren
         */
        List<Long> kandidatIds = kandidatMatches.stream()
                .map(km -> km.kandidat().getId())
                .toList();

        List<KandidatAufWahlliste> links =
                kandidatAufWahllisteRepository.findByKandidatIdIn(kandidatIds);

        Map<Long, List<KandidatAufWahlliste>> linksByKandidat = links.stream()
                .filter(l -> l.getKandidat() != null && l.getKandidat().getId() != null)
                .collect(Collectors.groupingBy(l -> l.getKandidat().getId()));

        /**
         * Struktur:
         * gremiumId -> (kandidatId -> Set<WahllisteDto>)
         */
        Map<Long, Map<Long, Set<KandidatWahllisteDto>>> wahllistenProGremium = new HashMap<>();

        for (KandidatMatch km : kandidatMatches) {

            Long kid = km.kandidat().getId();
            List<KandidatAufWahlliste> kandidatLinks =
                    linksByKandidat.getOrDefault(kid, List.of());

            for (KandidatAufWahlliste kaw : kandidatLinks) {

                Wahlliste wl = kaw.getWahlliste();
                if (wl == null) continue;

                Gremium g = wl.getGremium();
                if (g == null || g.getId() == null) continue;

                Long gid = g.getId();

                wahllistenProGremium
                        .computeIfAbsent(gid, __ -> new HashMap<>())
                        .computeIfAbsent(kid, __ -> new LinkedHashSet<>())
                        .add(new KandidatWahllisteDto(wl.getId(), wl.getName()));
            }
        }

        /**
         * Schritt 4:
         * DTO-Struktur aufbauen
         */
        Map<Long, Gremium> gremiumById = gremiumRepository.findAll().stream()
                .collect(Collectors.toMap(Gremium::getId, g -> g));

        Map<Long, Double> matchByKandidatId = kandidatMatches.stream()
                .collect(Collectors.toMap(km -> km.kandidat().getId(), KandidatMatch::match));

        Map<Long, Kandidat> kandidatById = kandidatMatches.stream()
                .collect(Collectors.toMap(km -> km.kandidat().getId(), KandidatMatch::kandidat));

        List<MatchResultGremiumDto> gremienDtos = new ArrayList<>();

        for (Map.Entry<Long, Map<Long, Set<KandidatWahllisteDto>>> entry : wahllistenProGremium.entrySet()) {

            Long gremiumId = entry.getKey();
            Gremium g = gremiumById.get(gremiumId);

            String gremiumName = (g != null) ? g.getName() : ("Gremium " + gremiumId);
            boolean requiresFachbereich = (g != null) && g.isRequiresFachbereich();

            List<MatchResultDto> kandidatenImGremium = new ArrayList<>();

            for (Map.Entry<Long, Set<KandidatWahllisteDto>> kEntry : entry.getValue().entrySet()) {

                Long kandidatId = kEntry.getKey();
                Kandidat kandidat = kandidatById.get(kandidatId);
                if (kandidat == null) continue;

                double match = matchByKandidatId.getOrDefault(kandidatId, 0.0);

                kandidatenImGremium.add(new MatchResultDto(
                        kandidat.getId(),
                        kandidat.getVorname() + " " + kandidat.getNachname(),
                        match,
                        kandidat.getFachbereich(),
                        kandidat.getBeschreibung(),
                        new ArrayList<>(kEntry.getValue())
                ));
            }

            kandidatenImGremium.sort((a, b) -> Double.compare(b.match(), a.match()));

            gremienDtos.add(new MatchResultGremiumDto(
                    gremiumId,
                    gremiumName,
                    requiresFachbereich,
                    kandidatenImGremium
            ));
        }

        gremienDtos.sort(Comparator.comparing(MatchResultGremiumDto::gremiumName));

        return gremienDtos;
    }

    /**
     * Berechnet das Matching zwischen einem Kandidatenwert
     * und einem Wählerwert.
     *
     * <p>
     * Algorithmus:
     * Abstand (0–9) → je größer die Distanz, desto geringer das Matching.
     * </p>
     *
     * @param candidateValue Wert des Kandidaten (1–10)
     * @param voterValue Wert des Wählers (1–10)
     * @return Matching in Prozent (0–100)
     */
    public double calculateMatchSingle(int candidateValue, int voterValue) {
        int distance = Math.abs(candidateValue - voterValue);
        return Math.max(0, 100 - distance * 10);
    }
}