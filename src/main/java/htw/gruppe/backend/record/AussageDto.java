package htw.gruppe.backend.record;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) zur Repräsentation einer Aussage.
 *
 * Dieses DTO wird im GET-Endpunkt für Aussagen verwendet und dient
 * der Übertragung der relevanten Felder einer Aussage vom Backend
 * zum Frontend.
 *
 * Enthaltene Informationen:
 * - id: Eindeutige ID der Aussage (nicht automatisch generiert)
 * - aussageText: Der eigentliche Text der Aussage
 * - aktiv: Kennzeichnet, ob die Aussage aktiv ist (Verwaltung durch Admin)
 *
 * Wird insbesondere für die Anzeige im Fragebogen verwendet.
 *
 * @author Tabatt
 * @author Dumke
 *
 * @author Eisner
 */
@Schema(description = "Aussage Data Transfer Object")
public record AussageDto(

        @Schema(
                description = "ID der Aussage (für die 15 definierten Aussagen, nicht automatisch generiert)",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Text der Aussage",
                example = "Wir möchten gerne ..."
        )
        String aussageText,

        @Schema(
                description = "Gibt an, ob die Aussage aktiv ist (Admin-Verwaltung)",
                example = "true"
        )
        Boolean aktiv

) {
}