package ru.practicum.shareit.item.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Valid
@Data
public class NewCommentDto {
    @NotNull
    @NotBlank
    private String text;
}
