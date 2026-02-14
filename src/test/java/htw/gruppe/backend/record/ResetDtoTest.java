package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ResetDtoTest {

    @Test
    void testCreation_ShouldSetValuesCorrectly() {
        // Positiver Testfall
        String token = "abc123";
        String newPassword = "securePassword";
        ResetDto dto = new ResetDto(token, newPassword);

        assertEquals(token, dto.token());
        assertEquals(newPassword, dto.newPassword());
    }

    @Test
    void testCreation_WithEmptyValues() {
        // Negativer Testfall: leere Strings
        ResetDto dto = new ResetDto("", "");
        assertEquals("", dto.token());
        assertEquals("", dto.newPassword());
    }

    @Test
    void testCreation_WithNullValues() {
        // Negativer Testfall: null Werte
        ResetDto dto = new ResetDto(null, null);
        assertNull(dto.token());
        assertNull(dto.newPassword());
    }
    @Test
    void testEqualsAndToString() {
        ResetDto dto1 = new ResetDto("abc", "123");
        ResetDto dto2 = new ResetDto("abc", "123");
        ResetDto dto3 = new ResetDto("def", "456");

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);

        String str = dto1.toString();
        assertTrue(str.contains("abc"));
        assertTrue(str.contains("123"));
    }

}
