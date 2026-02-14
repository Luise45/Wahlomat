package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KandidatenAntwortenDtoTest {


    // Positive Testfälle

    @Test
    void testRecordValues() {
        // Normale Werte
        KandidatenAntwortenDto dto = new KandidatenAntwortenDto(123L, 5);

        assertEquals(123L, dto.aussageId(), "aussageId sollte korrekt gesetzt sein");
        assertEquals(5, dto.answerValue(), "answerValue sollte korrekt gesetzt sein");
    }

    @Test
    void testRecordEquality() {
        KandidatenAntwortenDto dto1 = new KandidatenAntwortenDto(1L, 10);
        KandidatenAntwortenDto dto2 = new KandidatenAntwortenDto(1L, 10);
        KandidatenAntwortenDto dto3 = new KandidatenAntwortenDto(2L, 5);

        assertEquals(dto1, dto2, "Records mit gleichen Werten sollten gleich sein");
        assertNotEquals(dto1, dto3, "Records mit unterschiedlichen Werten sollten ungleich sein");
    }

    @Test
    void testRecordToString() {
        KandidatenAntwortenDto dto = new KandidatenAntwortenDto(42L, 7);
        String str = dto.toString();

        assertTrue(str.contains("42"));
        assertTrue(str.contains("7"));
    }


    // Negative / Extremfall-Testfälle


    @Test
    void testNullAussageId() {
        // Long Feld null
        KandidatenAntwortenDto dto = new KandidatenAntwortenDto(null, 5);

        assertNull(dto.aussageId(), "aussageId kann null sein");
        assertEquals(5, dto.answerValue(), "answerValue sollte korrekt bleiben");
    }

    @Test
    void testNegativeAnswerValue() {
        KandidatenAntwortenDto dto = new KandidatenAntwortenDto(1L, -10);

        assertEquals(-10, dto.answerValue(), "answerValue kann negativ sein");
        assertEquals(1L, dto.aussageId());
    }

    @Test
    void testMaxIntegerValue() {
        KandidatenAntwortenDto dto = new KandidatenAntwortenDto(1L, Integer.MAX_VALUE);

        assertEquals(Integer.MAX_VALUE, dto.answerValue(), "answerValue sollte Integer.MAX_VALUE korrekt speichern");
        assertEquals(1L, dto.aussageId());
    }

    @Test
    void testMinIntegerValue() {
        KandidatenAntwortenDto dto = new KandidatenAntwortenDto(1L, Integer.MIN_VALUE);

        assertEquals(Integer.MIN_VALUE, dto.answerValue(), "answerValue sollte Integer.MIN_VALUE korrekt speichern");
        assertEquals(1L, dto.aussageId());
    }
}
