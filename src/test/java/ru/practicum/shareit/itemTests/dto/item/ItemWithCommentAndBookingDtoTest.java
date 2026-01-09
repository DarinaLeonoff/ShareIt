package ru.practicum.shareit.itemTests.dto.item;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.item.dto.item.ItemWithCommentAndBookingDto;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ItemWithCommentAndBookingDtoTest {

    @Autowired
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ItemWithCommentAndBookingDto dto;

    @BeforeEach
    void setUp() {
        dto = Generators.generateItemWithCommentAndBooking(1L);
        dto = setCommentAndBooking(dto);
    }

    @Test
    void testValidDto() {
        Set<ConstraintViolation<ItemWithCommentAndBookingDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testInvalidName(String name) {
        dto.setName(name);

        Set<ConstraintViolation<ItemWithCommentAndBookingDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testInvalidDescription(String description) {
        dto.setDescription(description);

        Set<ConstraintViolation<ItemWithCommentAndBookingDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    void testInvalidAvailable(Boolean b) {
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

    private ItemWithCommentAndBookingDto setComments(ItemWithCommentAndBookingDto dto) {
        dto.setComments(List.of(Generators.generateCommentResponse()));
        return dto;
    }

    private ItemWithCommentAndBookingDto setBooking(ItemWithCommentAndBookingDto dto) {
        dto.setLastBooking(Generators.generateBookingDateDto());
        dto.setNextBooking(Generators.generateBookingDateDto());
        return dto;
    }

    private ItemWithCommentAndBookingDto setCommentAndBooking(ItemWithCommentAndBookingDto dto) {
        dto = setBooking(dto);
        dto = setComments(dto);

        return dto;
    }
}
