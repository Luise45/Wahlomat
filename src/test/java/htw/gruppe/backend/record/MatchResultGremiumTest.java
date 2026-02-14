package htw.gruppe.backend.record;


/**
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MatchResultGremiumTest {

    @Test
    void testRecordValues() {
        // Arrange
        MatchResult kandidat1 = new MatchResult("Max", 1, 0.9, "Beschreibung1", List.of(), "Informatik");
        MatchResult kandidat2 = new MatchResult("Anna", 2, 0.8, "Beschreibung2", List.of(), "Mathe");
        List<MatchResult> kandidaten = List.of(kandidat1, kandidat2);

        MatchResultGremium gremium = new MatchResultGremium(10L, "Prüfungsausschuss", true, kandidaten);

        // Act & Assert
        assertEquals(10L, gremium.gremienId());
        assertEquals("Prüfungsausschuss", gremium.gremiumName());
        assertTrue(gremium.requiresFachbereich());
        assertEquals(kandidaten, gremium.kandidaten());
        assertEquals(2, gremium.kandidaten().size());
    }

    @Test
    void testRecordEquality() {
        MatchResult kandidat = new MatchResult("Max", 1, 0.9, "Beschreibung", List.of(), "Informatik");
        List<MatchResult> list1 = List.of(kandidat);
        List<MatchResult> list2 = List.of(kandidat);

        MatchResultGremium g1 = new MatchResultGremium(1L, "Gremium A", true, list1);
        MatchResultGremium g2 = new MatchResultGremium(1L, "Gremium A", true, list1);
        MatchResultGremium g3 = new MatchResultGremium(2L, "Gremium B", false, list2);

        assertEquals(g1, g2, "Records mit gleichen Werten sollten gleich sein");
        assertNotEquals(g1, g3, "Records mit unterschiedlichen Werten sollten ungleich sein");
    }

    @Test
    void testRecordToString() {
        MatchResult kandidat = new MatchResult("Max", 1, 0.9, "Beschreibung", List.of(), "Informatik");
        List<MatchResult> kandidaten = List.of(kandidat);

        MatchResultGremium gremium = new MatchResultGremium(1L, "Gremium A", true, kandidaten);

        String str = gremium.toString();

        assertTrue(str.contains("1"));
        assertTrue(str.contains("Gremium A"));
        assertTrue(str.contains("true"));
        assertTrue(str.contains("Max"));
    }
    @Test
    void testNullFields() {
        MatchResultGremium gremium = new MatchResultGremium(null, null, false, null);

        assertNull(gremium.gremienId());
        assertNull(gremium.gremiumName());
        assertFalse(gremium.requiresFachbereich());
        assertNull(gremium.kandidaten());
    }
    @Test
    void testEmptyKandidatenList() {
        MatchResultGremium gremium = new MatchResultGremium(1L, "Gremium A", true, List.of());

        assertNotNull(gremium.kandidaten());
        assertTrue(gremium.kandidaten().isEmpty());
    }


}
*/