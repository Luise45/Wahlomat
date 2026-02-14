package htw.gruppe.backend.record;

/**
 * Data Transfer Object zur Übermittlung einer einzelnen Antwort
 * eines Wählers auf eine Aussage.
 *
 * Dieses DTO wird verwendet, um die vom Wähler vergebene Bewertung
 * einer bestimmten Aussage an das Backend zu übertragen.
 *
 * @param aussageId  Die eindeutige ID der bewerteten Aussage
 * @param value      Der vom Wähler vergebene Wert (z. B. 1–10)
 *
 * @author Eisner
 */
public record VoterAnswerDto(
        Long aussageId,
        int value
) {}