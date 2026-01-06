package ru.practicum.shareit.ItemTests.dto.item;

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
import ru.practicum.shareit.item.dto.item.ItemResponseDto;

import java.util.Set;

@SpringBootTest
public class ItemResponseTest {

    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ItemResponseDto dto;

    @BeforeEach
    void setUp() {
        dto = Generators.generateItemResponse(1L);
    }

    @Test
    public void validationTest() {
        Set<ConstraintViolation<ItemResponseDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void validationNameTest() {
        dto.setId(0);

        Set<ConstraintViolation<ItemResponseDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void validationNameTest(String name) {
        dto.setName(name);

        Set<ConstraintViolation<ItemResponseDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void validationDescTest(String desc) {
        dto.setDescription(desc);

        Set<ConstraintViolation<ItemResponseDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    public void validationAvailableTest(Boolean b) {
        dto.setAvailable(b);

        Set<ConstraintViolation<ItemResponseDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }
}
