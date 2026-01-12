package ru.practicum.shareit.item;

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
import ru.practicum.shareit.item.dto.ItemRequest;

import java.util.Set;

@SpringBootTest
public class ItemRequestTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ItemRequest dto;

    @BeforeEach
    void setUp() {
        dto = ItemRequest.builder()
                .name("Name")
                .description("Description")
                .available(true)
                .build();
    }

    @Test
    public void validationTest() {
        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void validationNameTest(String name) {
        dto.setName(name);

        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void validationDescTest(String desc) {
        dto.setDescription(desc);

        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    public void validationAvailableTest(Boolean b) {
        dto.setAvailable(b);

        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    public void validateNullRequestId(Long requestId) {
        dto.setRequestId(requestId);

        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void validateRequestId() {
        dto.setRequestId(2L);

        Set<ConstraintViolation<ItemRequest>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }
}
