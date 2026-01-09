package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

//    private List<Answer> answers;
}
