package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WahllisteDtoTest {


    // Positive Testfälle

    @Test
    void testRecordValues() {
        KandidatDto k1 = new KandidatDto(1L, "Tom", "Tailor");
        KandidatDto k2 = new KandidatDto(2L, "Lisa", "Breite");
        List<KandidatDto> kandidaten = List.of(k1, k2);

        WahllisteDto dto = new WahllisteDto(100L, "Liste 1", true, kandidaten);

        assertEquals(100L, dto.id());
        assertEquals("Liste 1", dto.name());
        assertTrue(dto.valid());
        assertEquals(kandidaten, dto.kandidaten());
        assertEquals(2, dto.kandidaten().size());
    }

    @Test
    void testEqualsAndToString() {
        KandidatDto k = new KandidatDto(1L, "Tom", "Tailor");
        List<KandidatDto> list1 = List.of(k);
        List<KandidatDto> list2 = List.of(k);

        WahllisteDto dto1 = new WahllisteDto(100L, "Liste A", true, list1);
        WahllisteDto dto2 = new WahllisteDto(100L, "Liste A", true, list2);
        WahllisteDto dto3 = new WahllisteDto(101L, "Liste B", false, list2);

        assertEquals(dto1, dto2, "Records mit gleichen Werten sollten gleich sein");
        assertNotEquals(dto1, dto3, "Records mit unterschiedlichen Werten sollten ungleich sein");

        String str = dto1.toString();
        assertTrue(str.contains("100"));
        assertTrue(str.contains("Liste A"));
        assertTrue(str.contains("true"));
        assertTrue(str.contains("Tom"));
    }


    // Negative / Extremfälle

    @Test
    void testNullValues() {
        WahllisteDto dto = new WahllisteDto(null, null, false, null);

        assertNull(dto.id());
        assertNull(dto.name());
        assertFalse(dto.valid());
        assertNull(dto.kandidaten());
    }

    @Test
    void testEmptyKandidatenList() {
        WahllisteDto dto = new WahllisteDto(100L, "Liste 1", true, List.of());

        assertEquals(100L, dto.id());
        assertEquals("Liste 1", dto.name());
        assertTrue(dto.valid());
        assertNotNull(dto.kandidaten());
        assertTrue(dto.kandidaten().isEmpty(), "kandidaten Liste sollte leer sein");
    }

    @Test
    void testSpecialCharactersInName() {
        WahllisteDto dto = new WahllisteDto(101L, "L!sté #1", true, List.of());
        assertEquals("L!sté #1", dto.name());
    }


}
