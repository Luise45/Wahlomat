package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KandidatDtoTest {

    @Test
    void shouldCreateKandidatDtoWithValidData() {
        // Positiver Testfall: alle Werte gesetzt
        KandidatDto dto = new KandidatDto(1L, "Muster", "Max");

        assertEquals(1L, dto.id());
        assertEquals("Muster", dto.nachname());
        assertEquals("Max", dto.vorname());
    }

    @Test
    void shouldAllowNullId() {
        // Negativfall: id ist null
        KandidatDto dto = new KandidatDto(null, "Muster", "Max");

        assertNull(dto.id());
        assertEquals("Muster", dto.nachname());
        assertEquals("Max", dto.vorname());
    }

    @Test
    void shouldAllowNullNames() {
        // Negativfall: vorname und nachname sind null
        KandidatDto dto = new KandidatDto(1L, null, null);

        assertEquals(1L, dto.id());
        assertNull(dto.nachname());
        assertNull(dto.vorname());
    }

    @Test
    void shouldAllowAllNulls() {
        // Extremfall: alles null
        KandidatDto dto = new KandidatDto(null, null, null);

        assertNull(dto.id());
        assertNull(dto.nachname());
        assertNull(dto.vorname());
    }
    @Test
    void equalsAndHashCode_ShouldWorkCorrectly() {
        KandidatDto dto1 = new KandidatDto(1L, "Muster", "Max");
        KandidatDto dto2 = new KandidatDto(1L, "Muster", "Max");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
    @Test
    void notEquals_WhenDifferentValues() {
        KandidatDto dto1 = new KandidatDto(1L, "Muster", "Max");
        KandidatDto dto2 = new KandidatDto(2L, "Anders", "Lisa");

        assertNotEquals(dto1, dto2);
    }
    @Test
    void toString_ShouldContainValues() {
        KandidatDto dto = new KandidatDto(1L, "Muster", "Max");

        String str = dto.toString();
        assertTrue(str.contains("Muster"));
        assertTrue(str.contains("Max"));
    }


}
