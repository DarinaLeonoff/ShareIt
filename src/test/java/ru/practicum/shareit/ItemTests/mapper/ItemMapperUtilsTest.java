package ru.practicum.shareit.ItemTests.mapper;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.item.dto.item.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapperUtils;
import ru.practicum.shareit.item.model.Item;

@SpringBootTest
public class ItemMapperUtilsTest {

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
