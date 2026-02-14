/*package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KandidatErgebDtoTest {
/**

    // Positive Testfälle

    @Test
    void testRecordValues() {
        KandidatErgebDto dto = new KandidatErgebDto(1L, "Tom", "Mueller", "Liste 1", 3.5);

        assertEquals(1L, dto.id());
        assertEquals("Tom", dto.vorname());
        assertEquals("Mueller", dto.nachname());
        assertEquals("Liste 1", dto.wahllistenName());
        assertEquals(3.5, dto.score());
    }

    @Test
    void testEqualsAndToString() {
        KandidatErgebDto dto1 = new KandidatErgebDto(1L, "Tom", "Mueller", "Liste 1", 3.5);
        KandidatErgebDto dto2 = new KandidatErgebDto(1L, "Tom", "Mueller", "Liste 1", 3.5);
        KandidatErgebDto dto3 = new KandidatErgebDto(2L, "Anna", "Schmidt", "Liste 2", 4.0);

        assertEquals(dto1, dto2, "Records mit gleichen Werten sollten gleich sein");
        assertNotEquals(dto1, dto3, "Records mit unterschiedlichen Werten sollten ungleich sein");

        String str = dto1.toString();
        assertTrue(str.contains("1"));
        assertTrue(str.contains("Tom"));
        assertTrue(str.contains("Mueller"));
        assertTrue(str.contains("Liste 1"));
        assertTrue(str.contains("3.5"));
    }


    // Negative / Extremfälle

    @Test
    void testNullFields() {
        KandidatErgebDto dto = new KandidatErgebDto(null, null, null, null, 0.0);

        assertNull(dto.id());
        assertNull(dto.vorname());
        assertNull(dto.nachname());
        assertNull(dto.wahllistenName());
        assertEquals(0.0, dto.score());
    }

    @Test
    void testNegativeScore() {
        KandidatErgebDto dto = new KandidatErgebDto(1L, "Tom", "Mueller", "Liste 1", -5.0);

        assertEquals(-5.0, dto.score());
        assertEquals(1L, dto.id());
    }

    @Test
    void testMaxAndMinScore() {
        KandidatErgebDto dtoMax = new KandidatErgebDto(1L, "Tom", "Mueller", "Liste 1", Double.MAX_VALUE);
        KandidatErgebDto dtoMin = new KandidatErgebDto(1L, "Tom", "Mueller", "Liste 1", -Double.MAX_VALUE);

        assertEquals(Double.MAX_VALUE, dtoMax.score());
        assertEquals(-Double.MAX_VALUE, dtoMin.score());
    }

}
*/