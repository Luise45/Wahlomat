package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoterAnswerDtoTest {


    // GUTE FÄLLE

    @Test
    void shouldCreateDtoWithValidValues() {
        VoterAnswerDto dto = new VoterAnswerDto(1L, 3);

        assertEquals(1L, dto.aussageId());
        assertEquals(3, dto.value());
    }

    @Test
    void shouldBeEqualWhenValuesAreSame() {
        VoterAnswerDto dto1 = new VoterAnswerDto(1L, 3);
        VoterAnswerDto dto2 = new VoterAnswerDto(1L, 3);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void toStringShouldContainValues() {
        VoterAnswerDto dto = new VoterAnswerDto(5L, 1);

        String result = dto.toString();
        assertTrue(result.contains("5"));
        assertTrue(result.contains("1"));
    }


    // SCHLECHTE FÄLLE
    @Test
    void shouldAllowNullAussageId() {
        VoterAnswerDto dto = new VoterAnswerDto(null, 2);

        assertNull(dto.aussageId());
        assertEquals(2, dto.value());
    }

    @Test
    void shouldAllowNegativeOrZeroValue() {
        VoterAnswerDto dtoZero = new VoterAnswerDto(1L, 0);
        VoterAnswerDto dtoNegative = new VoterAnswerDto(2L, -5);

        assertEquals(0, dtoZero.value());
        assertEquals(-5, dtoNegative.value());
    }
}
