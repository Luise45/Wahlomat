package htw.gruppe.backend.record;

import io.swagger.v3.oas.annotations.media.Schema;

public record CompleteRegistrationRequest(
        @Schema(description = "token fuer login", example = "sdljgfengl") String token,
        @Schema(description = "vorname des Kndidaten", example = "Milla") String vorname,
        @Schema(description = "nachname des Kandidaten", example = "Hillery")  String nachname,
        @Schema(description = "Passwort", example = "sdsjdfgl")  String password
) {}
