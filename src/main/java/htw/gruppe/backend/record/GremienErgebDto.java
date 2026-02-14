/*
package htw.gruppe.backend.record;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Veraltetes Data Transfer Object (DTO) zur Darstellung von Wahlergebnissen
 * pro Gremium.
 *
 * Dieses DTO wurde ursprünglich verwendet, um für ein bestimmtes Gremium
 * eine Liste der gematchten Kandidaten inklusive deren Bewertung (Score)
 * an das Frontend zu übertragen.
 *
 * Die Implementierung wurde später durch eine neue Matching-Struktur
 * (z. B. MatchResultDto / MatchResultGremiumDto) ersetzt.
 *
 * Enthaltene Informationen:
 * - name: Name des Gremiums
 * - kandidaten: Liste der zugehörigen Kandidaten mit Ergebniswerten
 *
 * Diese Klasse ist nicht mehr aktiv und bleibt nur zu Dokumentations-
 * und Nachvollziehbarkeitszwecken im Projekt erhalten.
 *
 * @author Eisner
 * @author Dumke
 */

 /*
public record GremienErgebDto(

        @Schema(description = "Name des Gremiums", example = "Fachschaftsrat")
        String name,

        @Schema(description = "Liste der Kandidaten mit Wahlergebnis")
        List<KandidatErgebDto> kandidaten

) {
}
*/