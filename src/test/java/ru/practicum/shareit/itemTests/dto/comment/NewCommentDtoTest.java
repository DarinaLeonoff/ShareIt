package ru.practicum.shareit.itemTests.dto.comment;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;

import java.util.Set;

public class NewCommentDtoTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @ParameterizedTest
    @NullAndEmptySource
    public void validationText(String text) {
        CommentResponseDto dto = CommentResponseDto.builder().text("Valid comment text").build();

        Set<ConstraintViolation<CommentResponseDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }
}
