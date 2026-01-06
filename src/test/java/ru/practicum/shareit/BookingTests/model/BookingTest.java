package ru.practicum.shareit.BookingTests.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.util.Set;

@SpringBootTest
public class BookingTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void validationTest() {
        Booking booking = Generators.generateBooking(1L, BookingStatus.APPROVED);

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void falseIfItemIsNull() {
        Booking booking = Generators.generateBooking(1L, BookingStatus.APPROVED);
        booking.setItem(null);

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void falseIfBookerIsNull() {
        Booking booking = Generators.generateBooking(1L, BookingStatus.APPROVED);
        booking.setBooker(null);

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void falseIfStartIsNull() {
        Booking booking = Generators.generateBooking(1L, BookingStatus.APPROVED);
        booking.setStart(null);

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void falseIfEndIsNull() {
        Booking booking = Generators.generateBooking(1L, BookingStatus.APPROVED);
        booking.setEnd(null);

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void falseIfStatusIsNull() {
        Booking booking = Generators.generateBooking(1L, BookingStatus.APPROVED);
        booking.setStatus(null);

        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);
        Assertions.assertFalse(violations.isEmpty());
    }
}
