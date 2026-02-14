package htw.gruppe.backend.record;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


public record RegisterRequest(

        @NotBlank (message ="Matrikelnummer ist erforderlich")
        @Schema(description = "Matrikelnummer des Kandidaten", example = "123456")  String matrikelnummer

) {}
