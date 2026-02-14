/*package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
class MatchResultTest {

    @Test
    void testRecordValues() {
        // Arrange
        KandidatWahllisteDto dto1 = new KandidatWahllisteDto(1L, "Liste A");
        KandidatWahllisteDto dto2 = new KandidatWahllisteDto(2L, "Liste B");
        List<KandidatWahllisteDto> wahllisten = List.of(dto1, dto2);

        MatchResult result = new MatchResult(
                "Max Mustermann",
                123,
                0.85,
                "Beschreibung",
                wahllisten,
                "Informatik"
        );

        // Act & Assert
        assertEquals("Max Mustermann", result.kandidatName());
        assertEquals(123, result.KandidatenId());
        assertEquals(0.85, result.match());
        assertEquals("Beschreibung", result.beschreibung());
        assertEquals(wahllisten, result.wahllisten());
        assertEquals("Informatik", result.fachbereich());
    }

    @Test
    void testRecordEquality() {
        KandidatWahllisteDto dto = new KandidatWahllisteDto(1L, "Liste A");
        List<KandidatWahllisteDto> list1 = List.of(dto);
        List<KandidatWahllisteDto> list2 = List.of(dto);

        MatchResult r1 = new MatchResult("Max", 1, 0.9, "Desc", list1, "Informatik");
        MatchResult r2 = new MatchResult("Max", 1, 0.9, "Desc", list1, "Informatik");
        MatchResult r3 = new MatchResult("Anna", 2, 0.8, "Andere", list2, "Mathe");

        assertEquals(r1, r2, "Records mit gleichen Werten sollten gleich sein");
        assertNotEquals(r1, r3, "Records mit unterschiedlichen Werten sollten ungleich sein");
    }

    @Test
    void testRecordToString() {
        KandidatWahllisteDto dto = new KandidatWahllisteDto(1L, "Liste A");
        List<KandidatWahllisteDto> wahllisten = List.of(dto);

        MatchResult result = new MatchResult("Max", 1, 0.9, "Desc", wahllisten, "Informatik");
        String str = result.toString();

        assertTrue(str.contains("Max"));
        assertTrue(str.contains("1"));
        assertTrue(str.contains("0.9"));
        assertTrue(str.contains("Desc"));
        assertTrue(str.contains("Liste A"));
        assertTrue(str.contains("Informatik"));
    }
    @Test
    void testNullFields() {
        MatchResult result = new MatchResult(null, null, 0.0, null, null, null);

        assertNull(result.kandidatName());
        assertNull(result.KandidatenId());
        assertEquals(0.0, result.match());
        assertNull(result.beschreibung());
        assertNull(result.wahllisten());
        assertNull(result.fachbereich());
    }
    @Test
    void testEmptyWahllisten() {
        MatchResult result = new MatchResult("Max", 1, 0.5, "Desc", List.of(), "Informatik");

        assertNotNull(result.wahllisten());
        assertTrue(result.wahllisten().isEmpty());
    }
    @Test
    void testExtremeMatchValues() {
        MatchResult resultMax = new MatchResult("Max", 1, Double.MAX_VALUE, "Desc", List.of(), "Informatik");
        MatchResult resultMin = new MatchResult("Max", 1, -Double.MAX_VALUE, "Desc", List.of(), "Informatik");

        assertEquals(Double.MAX_VALUE, resultMax.match());
        assertEquals(-Double.MAX_VALUE, resultMin.match());
    }

}
*/