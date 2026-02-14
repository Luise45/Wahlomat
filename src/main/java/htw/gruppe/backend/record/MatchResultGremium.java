/*
package htw.gruppe.backend.record;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * EHEMALIGE Implementierung der Ergebnisseiten-Logik.
 *
 * Diese Klasse wurde ursprünglich zur Darstellung der Matching-Ergebnisse
 * pro Gremium verwendet. Im weiteren Projektverlauf wurde die
 * Ergebnisseiten-Architektur jedoch überarbeitet und durch die neuen
 * DTO-Klassen {@link MatchResultGremiumDto} und {@link MatchResultDto}
 * ersetzt.
 *
 * Grund der Ablösung:
 * - Trennung von interner Logik und API-Darstellung
 * - Sauberere DTO-Struktur
 * - Vereinheitlichung der Naming-Convention
 * - Verbesserte Swagger-Dokumentation
 *
 * Die Klasse bleibt zu Dokumentationszwecken im Repository erhalten,
 * wird jedoch nicht mehr verwendet.
 *
 * @author Eisner
 * @author Dumke
 */


/*
public record MatchResultGremium(

        @Schema(description = "id", example = "1") Long gremienId,
        @Schema(description = "Name der Gremien", example = "Fachschaftsrat") String gremiumName,
        @Schema(description = "braucht das Gremium eine Fachbreich?", example = "false") boolean requiresFachbereich,
        @Schema(description = "Liste der Maching Kandidaten", example = "") List<MatchResult> kandidaten
) {
    public long getGremiumId() {
        return gremienId;
    }
}
*/