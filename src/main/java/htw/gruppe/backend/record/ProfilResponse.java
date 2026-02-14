package htw.gruppe.backend.record;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProfilResponse(
        @Schema(description = "Vorname des Kandidaten", example = "Anna")  String vorname,
        @Schema(description = "Nachname des Kandidaten", example = "XX")    String nachname,
        @Schema(description = "Fachbereich 1-4", example = "")   String fachbereich,
        @Schema(description = "", example = "sdljgfengl")  String studiengang,
        @Schema(description = "Matrikelnummer des Kandidaten", example = "123456") String matrikelnummer,
        @Schema(description = "Beschreibung des Kandidaten", example = "") String beschreibung
) { }
