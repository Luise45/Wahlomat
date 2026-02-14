package htw.gruppe.backend.record;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RegisterRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidRegisterRequest() {
        // Positiver Testfall
        RegisterRequest request = new RegisterRequest("s0485684");
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty(), "Es sollten keine Validierungsfehler auftreten");
    }

    @Test
    void testEmptyMatrikelnummer_ShouldFailValidation() {
        // Negativer Testfall: leere Matrikelnummer
        RegisterRequest request = new RegisterRequest("");
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Leere Matrikelnummer sollte Validierungsfehler erzeugen");
        assertEquals("Matrikelnummer ist erforderlich", violations.iterator().next().getMessage());
    }

    @Test
    void testNullMatrikelnummer_ShouldFailValidation() {
        // Negativer Testfall: null Matrikelnummer
        RegisterRequest request = new RegisterRequest(null);
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Null Matrikelnummer sollte Validierungsfehler erzeugen");
        assertEquals("Matrikelnummer ist erforderlich", violations.iterator().next().getMessage());
    }
    @Test
    void testWhitespaceMatrikelnummer_ShouldFailValidation() {
        RegisterRequest request = new RegisterRequest("   ");
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty(), "Nur Leerzeichen sollten Validierungsfehler erzeugen");
        assertEquals("Matrikelnummer ist erforderlich", violations.iterator().next().getMessage());
    }

}
