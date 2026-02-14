package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KandidatWahllisteDtoTest {


    // Positive Testfälle

    @Test
    void testRecordValues() {
        KandidatWahllisteDto dto = new KandidatWahllisteDto(1L, "Liste A");

        assertEquals(1L, dto.id());
        assertEquals("Liste A", dto.name());
    }

    @Test
    void testEqualsAndToString() {
        KandidatWahllisteDto dto1 = new KandidatWahllisteDto(1L, "Liste A");
        KandidatWahllisteDto dto2 = new KandidatWahllisteDto(1L, "Liste A");
        KandidatWahllisteDto dto3 = new KandidatWahllisteDto(2L, "Liste B");

        // Equals / hashCode
        assertEquals(dto1, dto2, "Records mit gleichen Werten sollten gleich sein");
        assertNotEquals(dto1, dto3, "Records mit unterschiedlichen Werten sollten ungleich sein");

        // toString
        String str = dto1.toString();
        assertTrue(str.contains("1"));
        assertTrue(str.contains("Liste A"));
    }


    // Negative / Extremfälle

    @Test
    void testNullIdAndName() {
        KandidatWahllisteDto dto = new KandidatWahllisteDto(null, null);

        assertNull(dto.id(), "id kann null sein");
        assertNull(dto.name(), "name kann null sein");
    }

    @Test
    void testEmptyName() {
        KandidatWahllisteDto dto = new KandidatWahllisteDto(1L, "");

        assertEquals(1L, dto.id());
        assertEquals("", dto.name(), "Name kann leer sein");
    }
}
