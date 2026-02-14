package htw.gruppe.backend.record;

import java.util.List;

/**
 * Data Transfer Object für das Ergebnis eines Matching-Vorgangs.
 *
 * Dieses DTO wird verwendet, um die berechnete Übereinstimmung
 * zwischen einem Wähler und einem Kandidaten an das Frontend
 * zu übertragen.
 *
 * Immutable DTO zur strukturierten Rückgabe der Matching-Ergebnisse.
 *
 * @param kandidatId   Die eindeutige ID des Kandidaten
 * @param kandidatName Vollständiger Name des Kandidaten
 * @param match        Prozentuale Übereinstimmung (Wert zwischen 0 und 100)
 * @param fachbereich  Fachbereich des Kandidaten (optional)
 * @param beschreibung Profilbeschreibung des Kandidaten
 * @param wahllisten   Liste der Wahllisten, für die der Kandidat kandidiert
 *
 * @author Eisner
 */
public record MatchResultDto(
        Long kandidatId,
        String kandidatName,
        double match,
        String fachbereich,
        String beschreibung,
        List<KandidatWahllisteDto> wahllisten
) {}