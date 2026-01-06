package ru.practicum.shareit.BookingTests.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.booking.dto.BookingRequestDto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookingRequestDtoTest {
    @Autowired
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private BookingRequestDto bookingRequestDto;

    @BeforeEach
    void setUp() {
        bookingRequestDto = Generators.generateBookingRequest(1L);
    }

    @Test
    void testValidDto() {
        Set<ConstraintViolation<BookingRequestDto>> violations = validator.validate(bookingRequestDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testAllNull() {
        bookingRequestDto.setItemId(0L);
        bookingRequestDto.setStart(null);
        bookingRequestDto.setEnd(null);

        Set<ConstraintViolation<BookingRequestDto>> violations = validator.validate(bookingRequestDto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.size() == 3);
    }
}
