package ru.practicum.shareit.ItemTests.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.mapper.ItemMapperImpl;
import ru.practicum.shareit.item.repository.InMemoryItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.InMemoryUserRepository;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@RequiredArgsConstructor
public class ItemServiceImplTest {
    private final ItemService itemService;
    private final UserService userService;
    private ItemMapper itemMapper;

//    @Test
//    void createAndGetItemTest() {
//        UserDto user = userService.createUser(Generators.generateUser(1L));
//        ItemDto dto = itemService.createItem(user.getId(), Generators.generateDto(1L));
//
//        List<ItemDto> dtos = itemService.getAllUserItems(user.getId());
//        ItemDto dto1 = itemService.getItemById(dto.getId());
//
//        assertEquals(1, dtos.size());
//        assertEquals(dto, dtos.getFirst());
//        assertEquals(dto, dto1);
//    }

    @Test
    void createItemWithWrongUserTest() {
        UserDto user = userService.createUser(Generators.generateUser(1L));

        assertThrows(NotFoundException.class, () -> {
            itemService.createItem(user.getId() + 1, Generators.generateDto(1L));
        });
    }

    @Test
    void updateItemTest() {
        UserDto user = userService.createUser(Generators.generateUser(1L));
        ItemDto itemDto = itemService.createItem(user.getId(), Generators.generateDto(1L));

        ItemDto dto1 = Generators.generateDto(itemDto.getId());
        dto1.setName("New name");

        ItemDto newItem = itemService.editItem(user.getId(), dto1, itemDto.getId());

        assertEquals(itemDto.getId(), newItem.getId());
        assertEquals(dto1.getName(), newItem.getName());
        assertEquals(itemDto.getDescription(), newItem.getDescription());
        assertEquals(itemDto.getAvailable(), newItem.getAvailable());
    }

    @Test
    void updateItemWithWrongUserTest() {
        UserDto user = userService.createUser(Generators.generateUser(1L));
        ItemDto itemDto = itemService.createItem(user.getId(), Generators.generateDto(1L));

        ItemDto dto1 = Generators.generateDto(itemDto.getId());
        dto1.setName("New name");

        assertThrows(NotFoundException.class, () -> {
            itemService.editItem(user.getId() + 1, dto1, itemDto.getId());
        });
    }

    @Test
    void searchTest() {
        for (int i = 0;
             i < 10;
             i++) {
            UserDto user = userService.createUser(Generators.generateUser((long) i));
            itemService.createItem(user.getId(), Generators.generateDto(1L));
        }
        String text = "text";
        ItemDto dto = itemService.createItem(2L, Generators.generateItemDtoForSearch(1, text));

        List<ItemDto> search = itemService.search(text);

        assertEquals(1, search.size());
        assertEquals(dto, search.getFirst());
    }

    @Test
    void searchWithUnableTest() {
        String text = "text";
        List<Long> id = new ArrayList<>();
        for (int i = 0;
             i < 10;
             i++) {
            UserDto user = userService.createUser(Generators.generateUser((long) i));
            boolean isText = false;
            ItemDto item;
            if (i == 2 || i == 8) {
                item = Generators.generateItemDtoForSearch(1, text);
                isText = true;
            } else {
                item = Generators.generateDto(1L);
            }
            if (i > 5) {
                item.setAvailable(false);
            }
            ItemDto created = itemService.createItem(user.getId(), item);
            if (isText) {
                id.add(created.getId());
            }
        }

        List<ItemDto> search = itemService.search(text);

        assertEquals(1, search.size());
        assertEquals(id.getFirst(), search.getFirst().getId());
        assertEquals(itemService.getItemById(id.getFirst()), search.getFirst());
    }

}
