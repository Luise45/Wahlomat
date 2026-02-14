package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompleteRegistrationRequestTest {

    @Test
    void constructor_ShouldSetAllFieldsCorrectly() {
        CompleteRegistrationRequest request = new CompleteRegistrationRequest(
                "token123",
                "Max",
                "Muster",
                "password123"
        );

        assertEquals("token123", request.token());
        assertEquals("Max", request.vorname());
        assertEquals("Muster", request.nachname());
        assertEquals("password123", request.password());
    }

    @Test
    void constructor_ShouldAllowNullValues() {
        CompleteRegistrationRequest request = new CompleteRegistrationRequest(null, null, null, null);

        assertNull(request.token());
        assertNull(request.vorname());
        assertNull(request.nachname());
        assertNull(request.password());
    }

    @Test
    void equalsAndHashCode_ShouldWorkCorrectly() {
        CompleteRegistrationRequest r1 = new CompleteRegistrationRequest("t", "V", "N", "p");
        CompleteRegistrationRequest r2 = new CompleteRegistrationRequest("t", "V", "N", "p");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void notEquals_WhenDifferentValues() {
        CompleteRegistrationRequest r1 = new CompleteRegistrationRequest("a", "b", "c", "d");
        CompleteRegistrationRequest r2 = new CompleteRegistrationRequest("x", "y", "z", "w");

        assertNotEquals(r1, r2);
    }
}
