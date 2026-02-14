package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfilResponseTest {

    @Test
    void testRecordValues() {
        // Arrange
        ProfilResponse response = new ProfilResponse(
                "Max",
                "Mustermann",
                "Informatik",
                "Software Engineering",
                "123456",
                "Beschreibung"
        );

        // Act & Assert
        assertEquals("Max", response.vorname());
        assertEquals("Mustermann", response.nachname());
        assertEquals("Informatik", response.fachbereich());
        assertEquals("Software Engineering", response.studiengang());
        assertEquals("123456", response.matrikelnummer());
        assertEquals("Beschreibung", response.beschreibung());
    }

    @Test
    void testRecordEquality() {
        ProfilResponse r1 = new ProfilResponse("Max", "Mustermann", "Informatik", "SE", "123456", "Beschreibung");
        ProfilResponse r2 = new ProfilResponse("Max", "Mustermann", "Informatik", "SE", "123456", "Beschreibung");
        ProfilResponse r3 = new ProfilResponse("Anna", "Musterfrau", "Mathe", "Maths", "654321", "Andere");

        assertEquals(r1, r2, "Records mit gleichen Werten sollten gleich sein");
        assertNotEquals(r1, r3, "Records mit unterschiedlichen Werten sollten ungleich sein");
    }

    @Test
    void testRecordToString() {
        ProfilResponse response = new ProfilResponse("Max", "Mustermann", "Informatik", "SE", "123456", "Beschreibung");
        String str = response.toString();

        assertTrue(str.contains("Max"));
        assertTrue(str.contains("Mustermann"));
        assertTrue(str.contains("Informatik"));
        assertTrue(str.contains("SE"));
        assertTrue(str.contains("123456"));
        assertTrue(str.contains("Beschreibung"));
    }
    @Test
    void testNullFields() {
        ProfilResponse response = new ProfilResponse(null, null, null, null, null, null);

        assertNull(response.vorname());
        assertNull(response.nachname());
        assertNull(response.fachbereich());
        assertNull(response.studiengang());
        assertNull(response.matrikelnummer());
        assertNull(response.beschreibung());
    }

}
