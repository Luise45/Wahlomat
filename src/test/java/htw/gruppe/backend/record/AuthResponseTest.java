package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {

    @Test
    void constructor_ShouldSetAllFieldsCorrectly() {
        AuthResponse response = new AuthResponse(
                "token123",
                "s059804",
                "login erfolgreich"
        );

        assertEquals("token123", response.token());
        assertEquals("s059804", response.matrikelnummer());
        assertEquals("login erfolgreich", response.message());
    }

    @Test
    void constructor_ShouldAllowNullValues() {
        AuthResponse response = new AuthResponse(null, null, null);

        assertNull(response.token());
        assertNull(response.matrikelnummer());
        assertNull(response.message());
    }

    @Test
    void equalsAndHashCode_ShouldWorkCorrectly() {
        AuthResponse r1 = new AuthResponse("t", "s059804", "ok");
        AuthResponse r2 = new AuthResponse("t", "s059804", "ok");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void notEquals_WhenDifferentValues() {
        AuthResponse r1 = new AuthResponse("token1", "s1", "ok");
        AuthResponse r2 = new AuthResponse("token2", "s2", "fail");

        assertNotEquals(r1, r2);
    }
}
