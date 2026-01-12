package ru.practicum.shareit.booking;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;

import java.time.LocalDateTime;
import java.util.Set;

@SpringBootTest
public class BookItemRequestDtoTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private BookItemRequestDto booking;

    @BeforeEach
    void setUp() {
        booking = BookItemRequestDto.builder()
                .itemId(1L)
                .start(LocalDateTime.now().plusMinutes(1))
                .end(LocalDateTime.now().plusDays(1))
                .build();
    }

    @Test
    void creatingValidation() {
        Set<ConstraintViolation<BookItemRequestDto>> violations = validator.validate(booking);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    void createBookingInPast() {
        booking.setStart(LocalDateTime.now().minusDays(2));

        Set<ConstraintViolation<BookItemRequestDto>> violations = validator.validate(booking);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void endBookingInPast() {
        booking.setEnd(LocalDateTime.now().minusDays(2));

        Set<ConstraintViolation<BookItemRequestDto>> violations = validator.validate(booking);
        Assertions.assertFalse(violations.isEmpty());
    }
}
