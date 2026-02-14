/*package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchRequestTest {

    @Test
    void testRecordValues() {
        // Arrange
        List<Integer> voterValues = List.of(1, 2, 3, 4);
        String fachbereich = "Informatik";

        // Act
        MatchRequest request = new MatchRequest(voterValues, fachbereich);

        // Assert
        assertEquals(voterValues, request.voterValues());
        assertEquals(fachbereich, request.fachbereich());
    }

    @Test
    void testRecordEquality() {
        List<Integer> list1 = List.of(1, 2, 3);
        List<Integer> list2 = List.of(1, 2, 3);
        List<Integer> list3 = List.of(4, 5, 6);

        MatchRequest r1 = new MatchRequest(list1, "Informatik");
        MatchRequest r2 = new MatchRequest(list2, "Informatik");
        MatchRequest r3 = new MatchRequest(list3, "Mathe");

        assertEquals(r1, r2, "Records mit gleichen Werten sollten gleich sein");
        assertNotEquals(r1, r3, "Records mit unterschiedlichen Werten sollten ungleich sein");
    }

    @Test
    void testRecordToString() {
        List<Integer> voterValues = List.of(5, 6, 7);
        MatchRequest request = new MatchRequest(voterValues, "Informatik");

        String str = request.toString();

        assertTrue(str.contains("5"));
        assertTrue(str.contains("6"));
        assertTrue(str.contains("7"));
        assertTrue(str.contains("Informatik"));
    }
    @Test
    void testNullValues() {
        MatchRequest request = new MatchRequest(null, null);

        assertNull(request.voterValues());
        assertNull(request.fachbereich());
    }
    @Test
    void testEmptyVoterValues() {
        MatchRequest request = new MatchRequest(List.of(), "Mathe");

        assertNotNull(request.voterValues());
        assertTrue(request.voterValues().isEmpty());
        assertEquals("Mathe", request.fachbereich());
    }


}
*/