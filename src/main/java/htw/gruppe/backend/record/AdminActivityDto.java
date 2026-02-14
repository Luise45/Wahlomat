package htw.gruppe.backend.record;

import java.time.Instant;

/**
 * DTO für die Ausgabe des Admin-Aktivitätsverlaufs
 * an das Frontend.
 *
 * @author Nguemezi
 */
public record AdminActivityDto(
        String description,
        Instant createdAt
) {}
