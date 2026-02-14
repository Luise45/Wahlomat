/*
package htw.gruppe.backend.record;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * EHEMALIGE Implementierung eines Matching-Ergebnisses für einen einzelnen Kandidaten.
 *
 * Diese Klasse war Teil der ursprünglichen Ergebnisseiten-Architektur.
 * Sie diente zur Darstellung eines einzelnen Kandidaten inklusive
 * Matching-Wert, Beschreibung, Wahllisten und Fachbereich.
 *
 * Im späteren Projektverlauf wurde die Matching-Logik sowie die DTO-Struktur
 * überarbeitet. Diese Klasse wurde durch die neuen DTOs
 * {@link MatchResultDto} und {@link MatchResultGremiumDto} ersetzt,
 * um eine klarere Trennung zwischen interner Logik und API-Ausgabe
 * zu gewährleisten.
 *
 * Gründe für die Ablösung:
 * - Vereinheitlichte Benennung (DTO-Konvention)
 * - Konsistente Typisierung (Long statt Integer)
 * - Bessere Strukturierung der Ergebnisse pro Gremium
 * - Vereinfachte Wartbarkeit
 *
 * Die Klasse bleibt ausschließlich aus Dokumentationszwecken im Repository,
 * wird jedoch nicht mehr aktiv verwendet.
 *
 * @author Eisner
 * @author Dumke
 */

/*
public record MatchResult(
        @Schema(description = "Kandidaten Name", example = "Max") String kandidatName,
        @Schema(description = "id", example = "1") Integer KandidatenId,
        @Schema(description = "Das Matching des Kandidaten fuer den Waehler", example = "67%") double match,
        @Schema(description = "Beschreibung des Kandidaten", example = "Ich finde super, dass..") String beschreibung,
        @Schema(description = "Kandidaten der Wahlliste", example = "") List<KandidatWahllisteDto> wahllisten,
        @Schema(description = "Fachbereich", example = "Elektrotechnik") String fachbereich
) { }

*/