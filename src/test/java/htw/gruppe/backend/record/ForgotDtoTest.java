package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ForgotDtoTest {

    @Test
    void constructor_ShouldSetMatrikelnummerCorrectly() {
        ForgotDto dto = new ForgotDto("s059804");
        assertEquals("s059804", dto.matrikelnummer());
    }

    @Test
    void constructor_ShouldAllowNull() {
        ForgotDto dto = new ForgotDto(null);
        assertNull(dto.matrikelnummer());
    }

    @Test
    void equalsAndHashCode_ShouldWorkCorrectly() {
        ForgotDto dto1 = new ForgotDto("s123");
        ForgotDto dto2 = new ForgotDto("s123");
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void notEquals_WhenDifferentValues() {
        ForgotDto dto1 = new ForgotDto("s123");
        ForgotDto dto2 = new ForgotDto("s999");
        assertNotEquals(dto1, dto2);
    }
}
