/*package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GremienErgebDtoTest {


    // Positive Testfälle
    @Test
    void testRecordValues() {
        KandidatErgebDto k1 = new KandidatErgebDto(1L, "Tom", "Mueller", "Liste 1", 3.5);
        KandidatErgebDto k2 = new KandidatErgebDto(2L, "Lisa", "Breite", "Liste 2", 4.0);

        List<KandidatErgebDto> kandidaten = List.of(k1, k2);
        GremienErgebDto dto = new GremienErgebDto("Fachschaftsrat", kandidaten);

        assertEquals("Fachschaftsrat", dto.name());
        assertEquals(kandidaten, dto.kandidaten());
        assertEquals(2, dto.kandidaten().size());
    }

    @Test
    void testEqualsAndToString() {
        KandidatErgebDto k = new KandidatErgebDto(1L, "Tom", "Mueller", "Liste 1", 3.5);
        List<KandidatErgebDto> list1 = List.of(k);
        List<KandidatErgebDto> list2 = List.of(k);

        GremienErgebDto dto1 = new GremienErgebDto("Gremium A", list1);
        GremienErgebDto dto2 = new GremienErgebDto("Gremium A", list2);
        GremienErgebDto dto3 = new GremienErgebDto("Gremium B", list2);

        assertEquals(dto1, dto2, "Records mit gleichen Werten sollten gleich sein");
        assertNotEquals(dto1, dto3, "Records mit unterschiedlichen Werten sollten ungleich sein");

        String str = dto1.toString();
        assertTrue(str.contains("Gremium A"));
        assertTrue(str.contains("Tom"));
        assertTrue(str.contains("Mueller"));
        assertTrue(str.contains("Liste 1"));
    }


    // Negative / Extremfälle
    @Test
    void testNullNameAndEmptyKandidaten() {
        GremienErgebDto dto = new GremienErgebDto(null, List.of());

        assertNull(dto.name(), "name kann null sein");
        assertNotNull(dto.kandidaten(), "kandidaten sollte nicht null sein");
        assertTrue(dto.kandidaten().isEmpty(), "kandidaten Liste sollte leer sein");
    }

    @Test
    void testNullKandidatenList() {
        GremienErgebDto dto = new GremienErgebDto("Fachschaftsrat", null);

        assertEquals("Fachschaftsrat", dto.name());
        assertNull(dto.kandidaten(), "kandidaten kann null sein");
    }

}
*/