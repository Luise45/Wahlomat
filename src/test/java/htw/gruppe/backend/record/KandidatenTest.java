package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KandidatenTest {

    @Test
    void shouldCreateKandidatenDtoWithAllValues() {
        // Positiver Fall: alle Werte gesetzt
        KandidatWahllisteDto wahllisteDto = new KandidatWahllisteDto(1L, "Liste A");
        Kandidaten kandidat = new Kandidaten(
                "s0485684",
                "FB4",
                "Lotta",
                "Meagi",
                "Informatik",
                "Beschreibungstext",
                List.of(wahllisteDto)
        );

        assertEquals("s0485684", kandidat.matrikelnummer());
        assertEquals("FB4", kandidat.fachbereich());
        assertEquals("Lotta", kandidat.vorname());
        assertEquals("Meagi", kandidat.nachname());
        assertEquals("Informatik", kandidat.studiengang());
        assertEquals("Beschreibungstext", kandidat.beschreibung());
        assertEquals(1, kandidat.wahllisten().size());
        assertEquals("Liste A", kandidat.wahllisten().get(0).name());
    }

    @Test
    void shouldAllowNullValues() {
        // Negativfall: einige Werte null
        Kandidaten kandidat = new Kandidaten(
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertNull(kandidat.matrikelnummer());
        assertNull(kandidat.fachbereich());
        assertNull(kandidat.vorname());
        assertNull(kandidat.nachname());
        assertNull(kandidat.studiengang());
        assertNull(kandidat.beschreibung());
        assertNull(kandidat.wahllisten());
    }

    @Test
    void shouldAllowEmptyWahllisten() {
        // Negativfall: leere Wahllisten
        Kandidaten kandidat = new Kandidaten(
                "s0485684",
                "FB4",
                "Lotta",
                "Meagi",
                "Informatik",
                "Beschreibung",
                List.of()
        );

        assertNotNull(kandidat.wahllisten());
        assertTrue(kandidat.wahllisten().isEmpty());
    }
}
