package ru.practicum.shareit.item.dto.comment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Valid
@Data
public class CommentResponseDto {
    private long id;
    @NotNull
    @NotBlank
    private String text;
    @NotNull
    @NotBlank
    private String authorName;
    @NotNull
    private LocalDateTime created;
}
