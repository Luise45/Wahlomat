package htw.gruppe.backend.record;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) zur Repräsentation einer Wahlliste,
 * der ein Kandidat zugeordnet ist.
 *
 * Diese Klasse wird im Rahmen der Matching- und Ergebnislogik verwendet,
 * um dem Frontend die Wahllisteninformationen eines Kandidaten strukturiert
 * bereitzustellen.
 *
 * Enthaltene Informationen:
 * - id: Eindeutige Identifikation der Wahlliste
 * - name: Anzeigename der Wahlliste
 *
 * Dieses DTO dient ausschließlich der Datenübertragung zwischen Backend
 * und Frontend und enthält keine Geschäftslogik.
 *
 * @author Eisner
 * @author Dumke
 * @author Tabatt
 */

public record KandidatWahllisteDto(

        @Schema(description = "ID der Wahlliste", example = "1")
        Long id,

        @Schema(description = "Name der Wahlliste", example = "Liste 1")
        String name

) {}