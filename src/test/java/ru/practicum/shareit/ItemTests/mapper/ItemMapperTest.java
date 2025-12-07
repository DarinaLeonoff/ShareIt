package ru.practicum.shareit.ItemTests.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

public class ItemMapperTest {

    @Test
    public void convertTest() {
        Item item = Generators.generateItem(1L);
        long owner = item.getOwnerId();

        ItemDto dto = ItemMapper.mapToDto(item);
        Item item2 = ItemMapper.mapToItem(dto, owner);

        Assertions.assertEquals(item, item2);
    }

    @Test
    public void updateTest() {
        Item item = Generators.generateItem(1L);

        ItemDto itemDto = new ItemDto();
        itemDto.setId(1L);
        itemDto.setName("Dto test name");
        itemDto.setDescription("Dto test desc");
        itemDto.setAvailable(true);

        Item updated = ItemMapper.updateItem(item, itemDto);

        Assertions.assertEquals(itemDto.getId(), updated.getId());
        Assertions.assertEquals(item.getId(), updated.getId());
        Assertions.assertEquals(itemDto.getName(), updated.getName());
        Assertions.assertEquals(itemDto.getDescription(), updated.getDescription());
        Assertions.assertEquals(itemDto.getAvailable(), updated.isAvailable());
    }
}
