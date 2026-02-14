package htw.gruppe.backend.record;


import io.swagger.v3.oas.annotations.media.Schema;

public record ResetDto(
        @Schema(description = "token", example = "sdljgfengl")  String token,
        @Schema(description = "neues Passwort des Kandidaten", example = "Sonne24")  String newPassword) {
}
