package ru.practicum.shareit.item.dto.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Where
 */
@Data
@Valid
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {

    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    private Long requestId;
}
