package ru.practicum.shareit.ItemTests.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.service.UserServiceImpl;

//TODO To be fixed next sprint
@SpringBootTest
public class ItemServiceImplTest {
    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private UserServiceImpl userService;

//    @Test
//    @Transactional
//    void createAndGetItemTest() {
//        UserDto user = userService.createUser(Generators.generateUser(1L));
//        ItemDto dto = itemService.createItem(user.getId(), Generators.generateDto(1L));
//
//        List<ItemWithCommentAndBookingDto> dtos = itemBookingService.getAllUserItems(user.getId());
//        ItemWithCommentAndBookingDto dto1 = itemBookingService.getItemWithCommentById(dto.getId());
//
//        assertEquals(1, dtos.size());
//        assertEquals(dto, dtos.get(0));
//        assertEquals(dto, dto1);
//    }

//    @Test
//    void createItemWithWrongUserTest() {
//        UserDto user = userService.createUser(Generators.generateUser(1L));
//
//        assertThrows(NotFoundException.class, () -> {
//            itemService.createItem(user.getId() + 1, Generators.generateDto(1L));
//        });
//    }

//    @Test
//    void updateItemTest() {
//        UserDto user = userService.createUser(Generators.generateUser(1L));
//        ItemDto itemDto = itemService.createItem(user.getId(), Generators.generateDto(1L));
//
//        ItemDto dto1 = Generators.generateDto(itemDto.getId());
//        dto1.setName("New name");
//
//        ItemDto newItem = itemService.editItem(user.getId(), dto1, itemDto.getId());
//
//        assertEquals(itemDto.getId(), newItem.getId());
//        assertEquals(dto1.getName(), newItem.getName());
//        assertEquals(itemDto.getDescription(), newItem.getDescription());
//        assertEquals(itemDto.getAvailable(), newItem.getAvailable());
//    }

//    @Test
//    void updateItemWithWrongUserTest() {
//        UserDto user = userService.createUser(Generators.generateUser(1L));
//        ItemDto itemDto = itemService.createItem(user.getId(), Generators.generateDto(1L));
//
//        ItemDto dto1 = Generators.generateDto(itemDto.getId());
//        dto1.setName("New name");
//
//        assertThrows(NotFoundException.class, () -> {
//            itemService.editItem(user.getId() + 1, dto1, itemDto.getId());
//        });
//    }

//    @Test
//    void searchTest() {
//        for (int i = 0;
//             i < 10;
//             i++) {
//            UserDto user = userService.createUser(Generators.generateUser((long) i));
//            itemService.createItem(user.getId(), Generators.generateDto(1L));
//        }
//        String text = "text";
//        ItemDto dto = itemService.createItem(2L, Generators.generateItemDtoForSearch(1, text));
//
//        List<ItemDto> search = itemService.search(text);
//
//        assertEquals(1, search.size());
//        assertEquals(dto, search.get(0));
//    }

//    @Test
//    void searchWithUnableTest() {
//        String text = "text";
//        List<Long> id = new ArrayList<>();
//        for (int i = 0;
//             i < 10;
//             i++) {
//            UserDto user = userService.createUser(Generators.generateUser((long) i));
//            boolean isText = false;
//            ItemDto item;
//            if (i == 2 || i == 8) {
//                item = Generators.generateItemDtoForSearch(1, text);
//                isText = true;
//            } else {
//                item = Generators.generateDto(1L);
//            }
//            if (i > 5) {
//                item.setAvailable(false);
//            }
//            ItemDto created = itemService.createItem(user.getId(), item);
//            if (isText) {
//                id.add(created.getId());
//            }
//        }
//
//        List<ItemDto> search = itemService.search(text);
//
//        assertEquals(1, search.size());
//        assertEquals(id.get(0), search.get(0).getId());
//    }

}
