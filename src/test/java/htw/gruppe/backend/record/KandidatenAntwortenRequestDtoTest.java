package htw.gruppe.backend.record;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class KandidatenAntwortenRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    // Positive Testfälle

    @Test
    void testRecordValues() {
        KandidatenAntwortenRequestDto dto = new KandidatenAntwortenRequestDto(1L, 5);

        assertEquals(1L, dto.aussage_id());
        assertEquals(5, dto.answerValue());
    }

    @Test
    void testEqualsAndToString() {
        KandidatenAntwortenRequestDto dto1 = new KandidatenAntwortenRequestDto(1L, 5);
        KandidatenAntwortenRequestDto dto2 = new KandidatenAntwortenRequestDto(1L, 5);
        KandidatenAntwortenRequestDto dto3 = new KandidatenAntwortenRequestDto(2L, 10);

        assertEquals(dto1, dto2, "Records mit gleichen Werten sollten gleich sein");
        assertNotEquals(dto1, dto3, "Records mit unterschiedlichen Werten sollten ungleich sein");

        String str = dto1.toString();
        assertTrue(str.contains("1"));
        assertTrue(str.contains("5"));
    }


    // Negative / Validation Testfälle

    @Test
    void testNullAussageIdValidation() {
        KandidatenAntwortenRequestDto dto = new KandidatenAntwortenRequestDto(null, 5);

        Set<ConstraintViolation<KandidatenAntwortenRequestDto>> violations = validator.validate(dto);

        assertEquals(1, violations.size(), "aussage_id darf nicht null sein");
        ConstraintViolation<KandidatenAntwortenRequestDto> violation = violations.iterator().next();
        assertEquals("aussage_id", violation.getPropertyPath().toString());
    }

    @Test
    void testNegativeAnswerValue() {
        KandidatenAntwortenRequestDto dto = new KandidatenAntwortenRequestDto(1L, -10);

        Set<ConstraintViolation<KandidatenAntwortenRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "answerValue kann negativ sein, keine Validation");
        assertEquals(-10, dto.answerValue());
    }

    @Test
    void testMaxAndMinAnswerValue() {
        KandidatenAntwortenRequestDto dtoMax = new KandidatenAntwortenRequestDto(1L, Integer.MAX_VALUE);
        KandidatenAntwortenRequestDto dtoMin = new KandidatenAntwortenRequestDto(1L, Integer.MIN_VALUE);

        assertEquals(Integer.MAX_VALUE, dtoMax.answerValue());
        assertEquals(Integer.MIN_VALUE, dtoMin.answerValue());
    }
}
