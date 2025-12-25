package ru.practicum.shareit.ItemTests.dto.item;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.BookingDateDto;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
import ru.practicum.shareit.item.dto.item.ItemWithCommentAndBookingDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ItemWithCommentAndBookingDtoTest {

    @Autowired
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValidDto() {
        BookingDateDto booking = makeBookingDateDto();
        CommentResponseDto comment = makeCommentResponseDto();

        ItemWithCommentAndBookingDto dto = makeItemWithCommentAndBookingDto(booking, comment);
        assertTrue(validator.validate(dto).isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testInvalidName(String name) {
        BookingDateDto booking = makeBookingDateDto();
        CommentResponseDto comment = makeCommentResponseDto();

        ItemWithCommentAndBookingDto dto = makeItemWithCommentAndBookingDto(booking, comment);
        dto.setName(name);

        Set<ConstraintViolation<ItemWithCommentAndBookingDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testInvalidDescription(String description) {
        BookingDateDto booking = makeBookingDateDto();
        CommentResponseDto comment = makeCommentResponseDto();

        ItemWithCommentAndBookingDto dto = makeItemWithCommentAndBookingDto(booking, comment);
        dto.setDescription(description);

        Set<ConstraintViolation<ItemWithCommentAndBookingDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    void testInvalidAvailable(Boolean b) {
        // available не может быть null
        BookingDateDto booking = makeBookingDateDto();
        CommentResponseDto comment = makeCommentResponseDto();

        ItemWithCommentAndBookingDto dto = makeItemWithCommentAndBookingDto(booking, comment);
        dto.setAvailable(b);

        Set<ConstraintViolation<ItemWithCommentAndBookingDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    void testNullableFields() {
        ItemWithCommentAndBookingDto dto = ItemWithCommentAndBookingDto.builder()
                .id(1L)
                .name("Название")
                .description("Описание")
                .available(true)
                .lastBooking(null)
                .nextBooking(null)
                .comments(null)
                .build();

        assertTrue(validator.validate(dto).isEmpty());
    }

    @Test
    void testEmptyCommentsList() {
        ItemWithCommentAndBookingDto dto = ItemWithCommentAndBookingDto.builder()
                .id(1L)
                .name("Название")
                .description("Описание")
                .available(true)
                .comments(List.of())
                .build();

        assertTrue(validator.validate(dto).isEmpty());
    }

    private BookingDateDto makeBookingDateDto() {
        return BookingDateDto.builder()
                .start(LocalDateTime.now())
                .end(LocalDateTime.now().plusDays(1))
                .build();
    }

    private CommentResponseDto makeCommentResponseDto() {
        return CommentResponseDto.builder().id(1L).text("Хороший товар").authorName("Автор").created(LocalDateTime.now()).build();
    }

    private ItemWithCommentAndBookingDto makeItemWithCommentAndBookingDto(
            BookingDateDto booking, CommentResponseDto comment) {
        return ItemWithCommentAndBookingDto.builder().id(1L).name("Название").description("Описание").available(true).lastBooking(booking).nextBooking(booking).comments(List.of(comment)).build();
    }
}
