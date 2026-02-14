package htw.gruppe.backend.record;

import jakarta.validation.constraints.NotBlank;

public record ProfilRequest(
        @NotBlank(message = "Fachbereich darf nicht leer sein")
        String fachbereich,

        @NotBlank(message = "Studiengang darf nicht leer sein")
        String studiengang,

        @NotBlank(message = "Beschreibung darf nicht leer sein")
        String beschreibung
) { }
