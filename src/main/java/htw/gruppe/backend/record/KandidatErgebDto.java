/*
package htw.gruppe.backend.record;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) zur Darstellung eines Kandidaten
 * in der ursprünglichen Ergebnisseiten-Logik.
 *
 * Dieses DTO wurde verwendet, um die Matching-Ergebnisse
 * für einen einzelnen Kandidaten strukturiert an das Frontend
 * zu übergeben.
 *
 * Enthaltene Informationen:
 * - id: Eindeutige Identifikation des Kandidaten
 * - vorname: Vorname des Kandidaten
 * - nachname: Nachname des Kandidaten
 * - wahllistenName: Zugehörige Wahlliste
 * - score: Berechneter Matching-Score
 *
 * Hinweis:
 * Diese Klasse gehört zur alten Matching-Implementierung
 * und wird aktuell nicht mehr aktiv verwendet.
 *
 * @author Eisner
 * @author Dumke
 */

/*
public record KandidatErgebDto(

        @Schema(description = "ID des Kandidaten", example = "2")
        Long id,

        @Schema(description = "Vorname des Kandidaten", example = "Tom")
        String vorname,

        @Schema(description = "Nachname des Kandidaten", example = "Mueller")
        String nachname,

        @Schema(description = "Name der Wahlliste", example = "Liste 1")
        String wahllistenName,

        @Schema(description = "Matching-Bewertung", example = "3")
        double score

) {

    public double getScore() {
        return score;
    }

    public Long getId() { return id; }

    public String getVorname() { return vorname; }

    public String getNachname() { return nachname; }

}
*/