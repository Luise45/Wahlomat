package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AussageDtoTest {

    @Test
    void constructor_ShouldSetAllFieldsCorrectly() {
        AussageDto dto = new AussageDto(
                1L,
                "Wir möchten gerne mehr Praxis",
                true
        );

        assertEquals(1L, dto.id());
        assertEquals("Wir möchten gerne mehr Praxis", dto.aussageText());
        assertTrue(dto.aktiv());
    }

    @Test
    void constructor_ShouldAllowNullValues() {
        AussageDto dto = new AussageDto(null, null, null);

        assertNull(dto.id());
        assertNull(dto.aussageText());
        assertNull(dto.aktiv());
    }

    @Test
    void equalsAndHashCode_ShouldWorkCorrectly() {
        AussageDto dto1 = new AussageDto(1L, "Text", true);
        AussageDto dto2 = new AussageDto(1L, "Text", true);

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void notEquals_WhenDifferentValues() {
        AussageDto dto1 = new AussageDto(1L, "Text A", true);
        AussageDto dto2 = new AussageDto(2L, "Text B", false);

        assertNotEquals(dto1, dto2);
    }

}
