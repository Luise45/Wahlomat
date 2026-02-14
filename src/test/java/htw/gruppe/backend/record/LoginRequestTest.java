package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    private final Validator validator;

    public LoginRequestTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationForValidInput() {
        // Positiver Fall: beide Felder gesetzt
        LoginRequest request = new LoginRequest("s1234567", "meinPasswort");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "Es sollten keine Validierungsfehler auftreten");
    }

    @Test
    void shouldFailValidationIfMatrikelnummerIsBlank() {
        // Negativfall: matrikelnummer leer
        LoginRequest request = new LoginRequest("", "meinPasswort");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("matrikelnummer")));
    }

    @Test
    void shouldFailValidationIfPasswordIsBlank() {
        // Negativfall: password leer
        LoginRequest request = new LoginRequest("s1234567", "");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void shouldFailValidationIfBothFieldsBlank() {
        // Negativfall: beide Felder leer
        LoginRequest request = new LoginRequest("", "");
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        assertEquals(2, violations.size(), "Beide Felder sollten Validierungsfehler haben");
    }
    @Test
    void shouldFailValidationIfFieldsAreNull() {
        LoginRequest request = new LoginRequest(null, null);
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(request);

        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("matrikelnummer")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

}
