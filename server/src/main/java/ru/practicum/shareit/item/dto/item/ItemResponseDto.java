package ru.practicum.shareit.item.dto.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDto {
    private long id;
    private String name;
    private String description;
    private Boolean available;
}
