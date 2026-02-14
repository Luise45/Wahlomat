package htw.gruppe.backend.record;

import java.util.List;

/**
 * Data Transfer Object zur Gruppierung von Matching-Ergebnissen
 * nach Gremien.
 *
 * Dieses DTO bündelt mehrere {@link MatchResultDto} Objekte
 * unter einem bestimmten Gremium. Es wird verwendet, um dem
 * Frontend die strukturierten Matching-Ergebnisse pro Gremium
 * bereitzustellen.
 *
 * @param gremiumId             Die eindeutige ID des Gremiums
 * @param gremiumName           Der Name des Gremiums
 * @param requiresFachbereich   Gibt an, ob das Gremium fachbereichsbezogen ist
 * @param kandidaten            Liste der Matching-Ergebnisse der Kandidaten
 *
 * @author Eisner
 */
public record MatchResultGremiumDto(
        Long gremiumId,
        String gremiumName,
        boolean requiresFachbereich,
        List<MatchResultDto> kandidaten
) {}