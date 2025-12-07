package ru.practicum.shareit;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class Generators {

    public static Item generateItem(Long id) {
        Item item = new Item();
        item.setId(id);
        item.setName("Test");
        item.setDescription("Desc");
        item.setAvailable(true);
        item.setOwnerId(100L);
        return item;
    }

    public static User generateUser(Long i) {
        User user = new User();
        user.setId(i);
        user.setName("Test Name " + i);
        user.setEmail("test" + i + "@ya.ru");
        return user;
    }

    public static ItemDto generateDto(Long id) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(id);
        itemDto.setName("Dto test name");
        itemDto.setDescription("Dto test desc");
        itemDto.setAvailable(true);
        return itemDto;
    }

    public static Item generateItemForSearch(int i, String text) {
        Item item = new Item();
        if (i == 1 || i == 3) {
            item.setName("Dto test name " + text);
        } else {
            item.setName("Dto test name");
        }
        if (i == 2 || i == 3) {
            item.setDescription("Dto test desc " + text);
        } else {
            item.setDescription("Dto test desc");
        }
        item.setAvailable(true);
        return item;
    }

    public static ItemDto generateItemDtoForSearch(int i, String text) {
        ItemDto item = new ItemDto();
        if (i == 1 || i == 3) {
            item.setName("Dto test name " + text);
        } else {
            item.setName("Dto test name");
        }
        if (i == 2 || i == 3) {
            item.setDescription("Dto test desc " + text);
        } else {
            item.setDescription("Dto test desc");
        }
        item.setAvailable(true);
        return item;
    }
}
