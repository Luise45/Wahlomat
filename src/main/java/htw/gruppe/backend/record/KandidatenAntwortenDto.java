package htw.gruppe.backend.record;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Dto für KandidatenAntworten get Endpunkt in Controller Klasse
 * @param aussageId
 * @param answerValue
 */
public record KandidatenAntwortenDto(
        @Schema(description = "id fuer die Aussage", example = "10") Long aussageId,
        @Schema(description = "Wert der Antwort 1-10", example = "1") int answerValue ){
    
}

