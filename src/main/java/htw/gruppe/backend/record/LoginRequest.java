package htw.gruppe.backend.record;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO für Login-Anfragen.
 * Enthält die benötigten Zugangsdaten des Nutzers.
 */
public record LoginRequest(

        @NotBlank(message = "Matrikelnummer ist erforderlich")
        String matrikelnummer,

        @NotBlank(message = "Passwort ist erforderlich")
        String password
) {}
