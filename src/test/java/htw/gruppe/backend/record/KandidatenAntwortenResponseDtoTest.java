package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KandidatenAntwortenResponseDtoTest {


    // Positive Testfälle

    @Test
    void testRecordValues() {
        KandidatenAntwortenResponseDto dto = new KandidatenAntwortenResponseDto(1L, 10);

        assertEquals(1L, dto.aussage_id());
        assertEquals(10, dto.answerValue());
    }

    @Test
    void testEqualsAndToString() {
        KandidatenAntwortenResponseDto dto1 = new KandidatenAntwortenResponseDto(1L, 10);
        KandidatenAntwortenResponseDto dto2 = new KandidatenAntwortenResponseDto(1L, 10);
        KandidatenAntwortenResponseDto dto3 = new KandidatenAntwortenResponseDto(2L, 5);

        // Equals / hashCode
        assertEquals(dto1, dto2, "Records mit gleichen Werten sollten gleich sein");
        assertNotEquals(dto1, dto3, "Records mit unterschiedlichen Werten sollten ungleich sein");

        // toString
        String str = dto1.toString();
        assertTrue(str.contains("1"));
        assertTrue(str.contains("10"));
    }


    // Negative / Extremfälle

    @Test
    void testNullAussageId() {
        KandidatenAntwortenResponseDto dto = new KandidatenAntwortenResponseDto(null, 10);

        assertNull(dto.aussage_id(), "aussage_id kann null sein");
        assertEquals(10, dto.answerValue());
    }

    @Test
    void testNegativeAnswerValue() {
        KandidatenAntwortenResponseDto dto = new KandidatenAntwortenResponseDto(1L, -5);

        assertEquals(-5, dto.answerValue());
        assertEquals(1L, dto.aussage_id());
    }

    @Test
    void testMaxAndMinAnswerValue() {
        KandidatenAntwortenResponseDto dtoMax = new KandidatenAntwortenResponseDto(1L, Integer.MAX_VALUE);
        KandidatenAntwortenResponseDto dtoMin = new KandidatenAntwortenResponseDto(1L, Integer.MIN_VALUE);

        assertEquals(Integer.MAX_VALUE, dtoMax.answerValue());
        assertEquals(Integer.MIN_VALUE, dtoMin.answerValue());
    }
}
