package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MatchResultDtoTest {

    @Test
    void shouldCreateMatchResultDtoWithAllFields() {

        Long kandidatId = 1L;
        String kandidatName = "Max Mustermann";
        double match = 85.0;
        String fachbereich = "Informatik";
        String beschreibung = "Sehr hohe Übereinstimmung";

        KandidatWahllisteDto wahlliste =
                new KandidatWahllisteDto(10L, "Liste A");

        List<KandidatWahllisteDto> wahllisten = List.of(wahlliste);


        MatchResultDto dto = new MatchResultDto(
                kandidatId,
                kandidatName,
                match,
                fachbereich,
                beschreibung,
                wahllisten
        );


        assertEquals(kandidatId, dto.kandidatId());
        assertEquals(kandidatName, dto.kandidatName());
        assertEquals(match, dto.match());
        assertEquals(fachbereich, dto.fachbereich());
        assertEquals(beschreibung, dto.beschreibung());
        assertEquals(wahllisten, dto.wahllisten());
    }

    @Test
    void shouldAllowNullForOptionalFields() {

        MatchResultDto dto = new MatchResultDto(
                2L,
                "Erika Musterfrau",
                72.5,
                null,
                null,
                List.of()
        );


        assertNull(dto.fachbereich());
        assertNull(dto.beschreibung());
        assertNotNull(dto.wahllisten());
        assertTrue(dto.wahllisten().isEmpty());
    }

    @Test
    void shouldBeEqualWhenValuesAreTheSame() {
        MatchResultDto dto1 = new MatchResultDto(
                1L, "Max", 80.0, "FB1", "Text", List.of()
        );

        MatchResultDto dto2 = new MatchResultDto(
                1L, "Max", 80.0, "FB1", "Text", List.of()
        );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void toStringShouldContainImportantValues() {
        MatchResultDto dto = new MatchResultDto(
                1L, "Max", 80.0, "FB1", "Text", List.of()
        );

        String result = dto.toString();

        assertTrue(result.contains("Max"));
        assertTrue(result.contains("80.0"));
        assertTrue(result.contains("FB1"));
    }
    @Test
    void shouldNotBeEqualWhenValuesDiffer() {
        MatchResultDto dto1 = new MatchResultDto(
                1L, "Max", 80.0, "FB1", "Text", List.of()
        );

        MatchResultDto dto2 = new MatchResultDto(
                2L, "Anna", 70.0, "FB2", "Andere Beschreibung", List.of()
        );

        assertNotEquals(dto1, dto2);
    }
    @Test
    void shouldAllowMatchAtBoundaryValues() {
        MatchResultDto zero = new MatchResultDto(
                1L, "Max", 0.0, null, null, List.of()
        );

        MatchResultDto hundred = new MatchResultDto(
                2L, "Anna", 100.0, null, null, List.of()
        );

        assertEquals(0.0, zero.match());
        assertEquals(100.0, hundred.match());
    }
    @Test
    void shouldHandleNullWahllisten() {
        MatchResultDto dto = new MatchResultDto(
                1L, "Max", 80.0, "FB1", "Text", null
        );

        assertNull(dto.wahllisten());
    }

}
