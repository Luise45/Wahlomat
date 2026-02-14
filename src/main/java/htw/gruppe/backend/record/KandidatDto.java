package htw.gruppe.backend.record;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object (DTO) zur Darstellung eines Kandidaten.
 *
 * Dieses DTO wird verwendet, um grundlegende Kandidateninformationen
 * vom Backend an das Frontend zu übertragen.
 *
 * Enthaltene Informationen:
 * - id: Eindeutige Identifikationsnummer des Kandidaten
 * - nachname: Nachname des Kandidaten
 * - vorname: Vorname des Kandidaten
 *
 * Das DTO dient ausschließlich der Datenübertragung und enthält
 * keine Geschäftslogik.
 *
 * @author Tabatt
 * @author Dumke
 * @author Eisner
 */
public record KandidatDto(

        @Schema(description = "id des Kandidaten", example = "1")
        Long id,

        @Schema(description = "Nachname des Kandidaten", example = "Mustermann")
        String nachname,

        @Schema(description = "Vorname des Kandidaten", example = "Max")
        String vorname

) { }