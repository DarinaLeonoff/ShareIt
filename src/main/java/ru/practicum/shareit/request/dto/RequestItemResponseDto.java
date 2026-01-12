package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.item.AnswerDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestItemResponseDto {
    private long id;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private LocalDateTime created;

    @Builder.Default
    private List<AnswerDto> items = new ArrayList<>();
}
