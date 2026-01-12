package ru.practicum.shareit.requestTest.dto;

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
import ru.practicum.shareit.request.dto.RequestItemResponseDto;

import java.time.LocalDateTime;
import java.util.Set;

@SpringBootTest
public class RequestItemResponseDtoTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void creatingTest() {
        RequestItemResponseDto res = RequestItemResponseDto.builder()
                .id(1L)
                .description("Test")
                .created(LocalDateTime.now())
                .build();
        Set<ConstraintViolation<RequestItemResponseDto>> violations = validator.validate(res);
        Assertions.assertTrue(violations.isEmpty());
        Assertions.assertTrue(res.getItems().isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testEmptyDescription(String description) {
        RequestItemResponseDto res = RequestItemResponseDto.builder()
                .id(1L)
                .description(description)
                .created(LocalDateTime.now())
                .build();
        Set<ConstraintViolation<RequestItemResponseDto>> violations = validator.validate(res);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    void testEmptyCreated(LocalDateTime created) {
        RequestItemResponseDto res = RequestItemResponseDto.builder()
                .id(1L)
                .description("Test")
                .created(created)
                .build();
        Set<ConstraintViolation<RequestItemResponseDto>> violations = validator.validate(res);
        Assertions.assertFalse(violations.isEmpty());
    }
}
