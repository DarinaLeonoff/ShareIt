package ru.practicum.shareit.itemTests.dto.comment;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@SpringBootTest
class CommentResponseDtoTest {
    @Autowired
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private CommentResponseDto commentResponseDto;

    @BeforeEach
    void setUp() {
        commentResponseDto = Generators.generateCommentResponse();
    }

    @Test
    void testValidDto() {
        Set<ConstraintViolation<CommentResponseDto>> violations = validator.validate(commentResponseDto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testInvalidText(String text) {
        commentResponseDto.setText(text);

        Set<ConstraintViolation<CommentResponseDto>> violations = validator.validate(commentResponseDto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void testInvalidAuthorName(String name) {
        commentResponseDto.setAuthorName(name);

        Set<ConstraintViolation<CommentResponseDto>> violations = validator.validate(commentResponseDto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    void testInvalidCreated(LocalDateTime ldt) {
        commentResponseDto.setCreated(ldt);

        Set<ConstraintViolation<CommentResponseDto>> violations = validator.validate(commentResponseDto);
        Assertions.assertFalse(violations.isEmpty());
    }
}

