package htw.gruppe.backend.record;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Data Transfer Object (DTO) zur Darstellung eines Gremiums
 * inklusive der zugehörigen Wahllisten.
 *
 * Dieses DTO wird verwendet, um Gremien-Daten strukturiert
 * vom Backend an das Frontend zu übertragen.
 *
 * Enthaltene Informationen:
 * - name: Name des Gremiums
 * - wahllisten: Liste der Wahllisten, die zu diesem Gremium gehören
 *
 * Wird insbesondere im Kontext der Ergebnisdarstellung
 * und der Administrationsfunktionen verwendet.
 *
 * @author Eisner
 * @author Dumke
 * @author Tabatt
 */
public record GremiumDto(

        @Schema(description = "Name des Gremiums", example = "Fachschaftsrat")
        String name,

        @Schema(description = "Liste der Wahllisten")
        List<WahllisteDto> wahllisten

) {}