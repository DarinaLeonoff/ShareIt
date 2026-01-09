package ru.practicum.shareit.requestTest.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.request.dto.RequestItemDto;

import java.util.Set;

@Slf4j
@SpringBootTest
public class RequestItemDtoTest {

    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testCreating() {
        RequestItemDto req = RequestItemDto.builder().description("test").build();

        Set<ConstraintViolation<RequestItemDto>> violations = validator.validate(req);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testWrongDescription(String description) {
        RequestItemDto req = RequestItemDto.builder().description(description).build();

        Set<ConstraintViolation<RequestItemDto>> violations = validator.validate(req);
        Assertions.assertFalse(violations.isEmpty());
    }
}
