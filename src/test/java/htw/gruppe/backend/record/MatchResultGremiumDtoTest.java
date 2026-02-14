package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchResultGremiumDtoTest {

    @Test
    void shouldCreateGremiumDtoWithFullData() {
        // Zwei Kandidaten mit allen Feldern
        MatchResultDto kandidat1 = new MatchResultDto(
                1L, "Max Mustermann", 90.0, "Informatik", "Sehr gute Übereinstimmung", List.of()
        );
        MatchResultDto kandidat2 = new MatchResultDto(
                2L, "Anna Musterfrau", 75.0, "Mathe", "Gute Übereinstimmung", List.of()
        );
        List<MatchResultDto> kandidaten = List.of(kandidat1, kandidat2);


        MatchResultGremiumDto gremium = new MatchResultGremiumDto(10L, "Prüfungsausschuss", true, kandidaten);


        assertEquals(10L, gremium.gremiumId());
        assertEquals("Prüfungsausschuss", gremium.gremiumName());
        assertTrue(gremium.requiresFachbereich());
        assertEquals(kandidaten, gremium.kandidaten());
        assertEquals(2, gremium.kandidaten().size());
    }

    @Test
    void shouldHandleEmptyKandidatenList() {
        MatchResultGremiumDto gremium = new MatchResultGremiumDto(5L, "Gremium B", false, List.of());

        assertNotNull(gremium.kandidaten());
        assertTrue(gremium.kandidaten().isEmpty());
    }

    @Test
    void shouldHandleNullOptionalFieldsInKandidaten() {
        // Kandidat mit null optionalen Feldern
        MatchResultDto kandidat = new MatchResultDto(
                3L, "Test Kandidat", 50.0, null, null, List.of()
        );

        MatchResultGremiumDto gremium = new MatchResultGremiumDto(1L, "Gremium C", true, List.of(kandidat));

        assertNotNull(gremium.kandidaten());
        assertEquals(1, gremium.kandidaten().size());
        assertNull(gremium.kandidaten().get(0).fachbereich());
        assertNull(gremium.kandidaten().get(0).beschreibung());
    }

    @Test
    void shouldBeEqualForSameValues() {
        MatchResultDto kandidat = new MatchResultDto(1L, "Max", 80.0, "FB1", "Text", List.of());
        List<MatchResultDto> kandidaten = List.of(kandidat);

        MatchResultGremiumDto g1 = new MatchResultGremiumDto(1L, "Gremium A", true, kandidaten);
        MatchResultGremiumDto g2 = new MatchResultGremiumDto(1L, "Gremium A", true, kandidaten);

        assertEquals(g1, g2);
        assertEquals(g1.hashCode(), g2.hashCode());
    }

    @Test
    void shouldToStringContainValues() {
        MatchResultDto kandidat = new MatchResultDto(1L, "Max", 80.0, "FB1", "Text", List.of());
        MatchResultGremiumDto gremium = new MatchResultGremiumDto(1L, "Gremium A", true, List.of(kandidat));

        String str = gremium.toString();

        assertTrue(str.contains("1"));
        assertTrue(str.contains("Gremium A"));
        assertTrue(str.contains("true"));
        assertTrue(str.contains("Max"));
    }

    @Test
    void shouldHandleNullFieldsInGremium() {
        MatchResultGremiumDto gremium = new MatchResultGremiumDto(null, null, false, null);

        assertNull(gremium.gremiumId());
        assertNull(gremium.gremiumName());
        assertFalse(gremium.requiresFachbereich());
        assertNull(gremium.kandidaten());
    }
    @Test
    void shouldNotBeEqualWhenValuesDiffer() {
        MatchResultDto kandidat = new MatchResultDto(
                1L, "Max", 80.0, "FB1", "Text", List.of()
        );

        MatchResultGremiumDto g1 = new MatchResultGremiumDto(
                1L, "Gremium A", true, List.of(kandidat)
        );

        MatchResultGremiumDto g2 = new MatchResultGremiumDto(
                2L, "Gremium B", false, List.of()
        );

        assertNotEquals(g1, g2);
    }

}
