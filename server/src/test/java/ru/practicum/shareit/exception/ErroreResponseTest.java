package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exceptions.ErrorResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ErroreResponseTest {

    @Test
    void testValidConstructor() {
        ErrorResponse response = new ErrorResponse("Error", "Description");
        assertEquals("Error", response.getError());
        assertEquals("Description", response.getDescription());
    }

    @Test
    void testNullError() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ErrorResponse(null, "Description");
        });
    }

    @Test
    void testEmptyError() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ErrorResponse("", "Description");
        });
    }

    @Test
    void testToString() {
        ErrorResponse response = new ErrorResponse("Error", "Description");
        assertEquals("ErrorResponse(error=Error, description=Description)", response.toString());
    }

}
