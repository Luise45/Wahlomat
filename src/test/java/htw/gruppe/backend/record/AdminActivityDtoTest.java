package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class AdminActivityDtoTest {

    @Test
    void constructor_ShouldSetAllFieldsCorrectly() {
        Instant now = Instant.now();

        AdminActivityDto dto = new AdminActivityDto(
                "Kandidat erstellt",
                now
        );

        assertEquals("Kandidat erstellt", dto.description());
        assertEquals(now, dto.createdAt());
    }

    @Test
    void constructor_ShouldAllowNullValues() {
        AdminActivityDto dto = new AdminActivityDto(null, null);

        assertNull(dto.description());
        assertNull(dto.createdAt());
    }

    @Test
    void equalsAndHashCode_ShouldWorkCorrectly() {
        Instant now = Instant.now();

        AdminActivityDto dto1 = new AdminActivityDto("Test", now);
        AdminActivityDto dto2 = new AdminActivityDto("Test", now);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void notEquals_WhenDifferentValues() {
        AdminActivityDto dto1 = new AdminActivityDto("A", Instant.now());
        AdminActivityDto dto2 = new AdminActivityDto("B", Instant.now());

        assertNotEquals(dto1, dto2);
    }
}
