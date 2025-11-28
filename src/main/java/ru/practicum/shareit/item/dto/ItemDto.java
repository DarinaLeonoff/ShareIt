package ru.practicum.shareit.item.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Valid
public class ItemDto {
    private long id;
    @NotNull(message = "Необходимо указать название вещи.")
    private String name;
    private String description;
    private boolean available = true;
}
