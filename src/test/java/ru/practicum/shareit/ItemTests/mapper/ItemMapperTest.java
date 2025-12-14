package ru.practicum.shareit.ItemTests.mapper;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.mapper.ItemMapperImpl;
import ru.practicum.shareit.item.mapper.ItemMapperUtils;
import ru.practicum.shareit.item.model.Item;

@SpringBootTest
public class ItemMapperTest {

    private final ItemMapper itemMapper;

    @Autowired
    public ItemMapperTest(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }


    @Test
    public void convertTest() {
        Item item = Generators.generateItem(1L);
        long owner = item.getOwnerId();

        ItemDto dto = itemMapper.mapToDto(item);
        Item item2 = itemMapper.mapToItem(dto, owner);

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

        Item updated = ItemMapperUtils.updateItem(item, itemDto);

        Assertions.assertEquals(itemDto.getId(), updated.getId());
        Assertions.assertEquals(item.getId(), updated.getId());
        Assertions.assertEquals(itemDto.getName(), updated.getName());
        Assertions.assertEquals(itemDto.getDescription(), updated.getDescription());
        Assertions.assertEquals(itemDto.getAvailable(), updated.isAvailable());
    }
}
