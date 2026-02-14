/*
package htw.gruppe.backend.record;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/**
 * EHEMALIGE Request-Klasse für das Matching zwischen Wähler und Kandidaten.
 *
 * Diese Klasse wurde in einer frühen Version der Matching-Logik verwendet,
 * um die vom Wähler abgegebenen Bewertungen sowie den gewählten Fachbereich
 * an das Backend zu übermitteln.
 *
 * Ursprüngliche Funktion:
 * - Übertragung einer Liste von Bewertungswerten (voterValues)
 * - Optionaler Fachbereichsfilter
 * - Grundlage für die Berechnung der Matching-Prozentwerte
 *
 * Im späteren Projektverlauf wurde die Matching-Architektur überarbeitet.
 * Die Request-Struktur wurde angepasst, um:
 *
 * - eine klarere Trennung zwischen Frontend- und Backend-Modellen zu schaffen
 * - eine stabilere Typisierung zu ermöglichen
 * - eine flexiblere Erweiterbarkeit der Matching-Logik zu gewährleisten
 *
 * Diese Klasse ist nicht mehr aktiv im Einsatz und bleibt ausschließlich
 * aus Dokumentations- und Nachvollziehbarkeitsgründen im Repository.
 *
 * @author Eisner
 * @author Dumke
 */

 /*
public record MatchRequest(
        @Schema(description = "Liste der Werte fuer Aussagen", example = "(1,2,3,4,5,6)")
        List<Integer> voterValues,

        @Schema(description = "Fachbereich", example = "Informatik")
        String fachbereich
) {}
*/