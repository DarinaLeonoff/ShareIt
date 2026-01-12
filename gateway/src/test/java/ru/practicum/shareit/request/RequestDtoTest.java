package ru.practicum.shareit.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.request.dto.RequestDto;

import java.util.Set;

@SpringBootTest
public class RequestDtoTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private RequestDto request;

    @BeforeEach
    void setUp() {
        request = RequestDto.builder().description("Desc").build();
    }

    @Test
    void createValidationTest() {
        Set<ConstraintViolation<RequestDto>> violations = validator.validate(request);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void createInvalidRequestTest(String desc) {
        request.setDescription(desc);
        Set<ConstraintViolation<RequestDto>> violations = validator.validate(request);
        Assertions.assertFalse(violations.isEmpty());
    }
}
