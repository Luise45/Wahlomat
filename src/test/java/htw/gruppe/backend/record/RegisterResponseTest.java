package htw.gruppe.backend.record;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterResponseTest {

    @Test
    void testRecordValues() {
        // Arrange
        Long expectedId = 1L;
        String expectedMessage = "User registered successfully";

        // Act
        RegisterResponse response = new RegisterResponse(expectedId, expectedMessage);

        // Assert
        assertEquals(expectedId, response.id(), "ID sollte korrekt gesetzt sein");
        assertEquals(expectedMessage, response.message(), "Message sollte korrekt gesetzt sein");
    }

    @Test
    void testRecordEquality() {
        // Arrange
        RegisterResponse response1 = new RegisterResponse(1L, "OK");
        RegisterResponse response2 = new RegisterResponse(1L, "OK");
        RegisterResponse response3 = new RegisterResponse(2L, "Not OK");

        // Act & Assert
        assertEquals(response1, response2, "Records mit gleichen Werten sollten gleich sein");
        assertNotEquals(response1, response3, "Records mit unterschiedlichen Werten sollten ungleich sein");
    }

    @Test
    void testRecordToString() {
        // Arrange
        RegisterResponse response = new RegisterResponse(10L, "Test message");

        // Act
        String str = response.toString();

        // Assert
        assertTrue(str.contains("10"), "toString sollte die ID enthalten");
        assertTrue(str.contains("Test message"), "toString sollte die Message enthalten");
    }
    @Test
    void testNullValues() {
        RegisterResponse response = new RegisterResponse(null, null);

        assertNull(response.id());
        assertNull(response.message());
    }

}
