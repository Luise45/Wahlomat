package htw.gruppe.backend.record;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterResponse(
        @Schema(description = "id", example = "1") Long id,
        @Schema(description = "registration success", example = "Erfolgreich") String message
) {}
