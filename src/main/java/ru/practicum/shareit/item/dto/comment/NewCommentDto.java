package ru.practicum.shareit.item.dto.comment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Valid
@Builder
@Data
public class NewCommentDto {
    @NotNull
    @NotBlank
    private String text;
}
