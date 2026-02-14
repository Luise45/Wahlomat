package htw.gruppe.backend.record;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProfilRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testRecordValues() {
        // Arrange
        ProfilRequest request = new ProfilRequest("Informatik", "Software Engineering", "Beschreibung");

        // Act & Assert
        assertEquals("Informatik", request.fachbereich());
        assertEquals("Software Engineering", request.studiengang());
        assertEquals("Beschreibung", request.beschreibung());
    }

    @Test
    void testValidationFailsForBlankOrNull() {
        // Test mit leerem String
        ProfilRequest blankRequest = new ProfilRequest("", " ", null);

        Set<ConstraintViolation<ProfilRequest>> violations = validator.validate(blankRequest);

        assertEquals(3, violations.size(), "Alle 3 Felder sollten Validation-Fehler erzeugen");

        // Prüfen, dass jede Property einen Fehler hat
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("fachbereich")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("studiengang")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("beschreibung")));
    }

    @Test
    void testValidationPassesForValidValues() {
        ProfilRequest validRequest = new ProfilRequest("Informatik", "SE", "Beschreibung");

        Set<ConstraintViolation<ProfilRequest>> violations = validator.validate(validRequest);

        assertTrue(violations.isEmpty(), "Bei gültigen Werten sollten keine Validation-Fehler auftreten");
    }
    @Test
    void testValidationSingleFieldBlankOrNull() {
        // Fachbereich null
        ProfilRequest request1 = new ProfilRequest(null, "SE", "Beschreibung");
        Set<ConstraintViolation<ProfilRequest>> violations1 = validator.validate(request1);
        assertEquals(1, violations1.size());
        assertTrue(violations1.stream().anyMatch(v -> v.getPropertyPath().toString().equals("fachbereich")));

        // Studiengang leer
        ProfilRequest request2 = new ProfilRequest("Informatik", "", "Beschreibung");
        Set<ConstraintViolation<ProfilRequest>> violations2 = validator.validate(request2);
        assertEquals(1, violations2.size());
        assertTrue(violations2.stream().anyMatch(v -> v.getPropertyPath().toString().equals("studiengang")));

        // Beschreibung blank
        ProfilRequest request3 = new ProfilRequest("Informatik", "SE", " ");
        Set<ConstraintViolation<ProfilRequest>> violations3 = validator.validate(request3);
        assertEquals(1, violations3.size());
        assertTrue(violations3.stream().anyMatch(v -> v.getPropertyPath().toString().equals("beschreibung")));
    }

}
