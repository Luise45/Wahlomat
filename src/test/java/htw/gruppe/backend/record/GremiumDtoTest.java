package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GremiumDtoTest {

    // Positive Testfälle
    @Test
    void testRecordValues() {
        WahllisteDto wl1 = new WahllisteDto(1L, "Liste A", true, List.of(new KandidatDto(1L, "Tom", "Tailor")));
        WahllisteDto wl2 = new WahllisteDto(2L, "Liste B", false, List.of(new KandidatDto(2L, "Lisa", "Breite")));
        List<WahllisteDto> wahllisten = List.of(wl1, wl2);

        GremiumDto gremium = new GremiumDto("Fachschaftsrat", wahllisten);

        assertEquals("Fachschaftsrat", gremium.name());
        assertEquals(wahllisten, gremium.wahllisten());
        assertEquals(2, gremium.wahllisten().size());
    }

    @Test
    void testEqualsAndToString() {
        WahllisteDto wl = new WahllisteDto(1L, "Liste A", true, List.of(new KandidatDto(1L, "Tom", "Tailor")));
        List<WahllisteDto> list1 = List.of(wl);
        List<WahllisteDto> list2 = List.of(wl);

        GremiumDto g1 = new GremiumDto("Gremium A", list1);
        GremiumDto g2 = new GremiumDto("Gremium A", list2);
        GremiumDto g3 = new GremiumDto("Gremium B", list2);

        assertEquals(g1, g2, "Records mit gleichen Werten sollten gleich sein");
        assertNotEquals(g1, g3, "Records mit unterschiedlichen Werten sollten ungleich sein");

        String str = g1.toString();
        assertTrue(str.contains("Gremium A"));
        assertTrue(str.contains("Liste A"));
        assertTrue(str.contains("Tom"));
    }


    // Negative / Extremfälle
    @Test
    void testNullNameAndEmptyList() {
        GremiumDto gremium = new GremiumDto(null, List.of());

        assertNull(gremium.name(), "name kann null sein");
        assertNotNull(gremium.wahllisten(), "wahllisten sollte nicht null sein");
        assertTrue(gremium.wahllisten().isEmpty(), "wahllisten sollte leer sein");
    }

    @Test
    void testNullList() {
        GremiumDto gremium = new GremiumDto("Fachschaftsrat", null);

        assertEquals("Fachschaftsrat", gremium.name());
        assertNull(gremium.wahllisten(), "wahllisten kann null sein");
    }
}
