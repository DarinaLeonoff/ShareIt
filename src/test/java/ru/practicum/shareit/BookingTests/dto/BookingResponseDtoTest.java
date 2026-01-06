package ru.practicum.shareit.BookingTests.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.booking.dto.BookingResponseDto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BookingResponseDtoTest {
    @Autowired
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private BookingResponseDto bookingResponseDto;

    @BeforeEach
    void setUp() {
        bookingResponseDto = Generators.generateBookingResponse();
    }

    @Test
    void testValidDto() {
        Set<ConstraintViolation<BookingResponseDto>> violations = validator.validate(bookingResponseDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testAllNull() {
        bookingResponseDto.setItem(null);
        bookingResponseDto.setBooker(null);
        bookingResponseDto.setStart(null);
        bookingResponseDto.setEnd(null);
        bookingResponseDto.setStatus(null);
        Set<ConstraintViolation<BookingResponseDto>> violations = validator.validate(bookingResponseDto);
        Assertions.assertFalse(violations.isEmpty());
        assertTrue(violations.size() == 5);
    }


}
