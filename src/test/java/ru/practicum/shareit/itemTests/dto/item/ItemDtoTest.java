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
import ru.practicum.shareit.item.dto.item.ItemRequestDto;

import java.util.Set;

@SpringBootTest
public class ItemDtoTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ItemRequestDto dto;

    @BeforeEach
    void setUp() {
        dto = Generators.generateItemRequest();
    }

    @Test
    public void validationTest() {
        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void validationNameTest(String name) {
        dto.setName(name);

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void validationDescTest(String desc) {
        dto.setDescription(desc);

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    public void validationAvailableTest(Boolean b) {
        dto.setAvailable(b);

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    public void validateNullRequestId(Long requestId) {
        dto.setRequestId(requestId);

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void validateRequestId() {
        dto.setRequestId(2L);

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }
}
