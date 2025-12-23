package ru.practicum.shareit.item.dto.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemWIthCommentDto {
    private long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;

    List<CommentResponseDto> comments = new ArrayList<>();

}
