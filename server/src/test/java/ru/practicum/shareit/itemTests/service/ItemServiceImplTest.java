package ru.practicum.shareit.itemTests.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.item.AnswerDto;
import ru.practicum.shareit.item.dto.item.ItemRequestDto;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.item.repository.DbItemRepository;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.request.dto.RequestItemResponseDto;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.repository.DbUserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
public class ItemServiceImplTest {
    @Autowired
    private ItemServiceImpl itemService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RequestService requestService;

    @Mock
    private DbUserRepository userRepository;
    @Mock
    private DbItemRepository itemRepository;
    @Mock
    private RequestRepository requestRepository;

    private UserResponseDto user;
    private ItemResponseDto item;

    @BeforeEach
    void setUp() {
        user = userService.createUser(Generators.generateUserRequestDto());
        item = itemService.createItem(user.getId(), Generators.generateItemRequest());
    }

    @Test
    void createAndGetItemTest() {
        ItemResponseDto itemFromDb = itemService.getItemDtoById(item.getId());

        assertEquals(item, itemFromDb);
    }

    @Test
    void createItemWithWrongUserTest() {
        assertThrows(NotFoundException.class, () -> {
            itemService.createItem(user.getId() + 1, Generators.generateItemRequest());
        });
    }

    @Test
    void updateItemTest() {
        ItemRequestDto toEdit = Generators.generateItemRequest();
        toEdit.setName("New name");

        ItemResponseDto newItem = itemService.editItem(user.getId(), toEdit, item.getId());

        assertEquals(item.getId(), newItem.getId());
        assertEquals(toEdit.getName(), newItem.getName());
        assertEquals(item.getDescription(), newItem.getDescription());
        assertEquals(item.getAvailable(), newItem.getAvailable());
    }

    @Test
    void updateItemWithWrongUserTest() {
        ItemRequestDto toEdit = Generators.generateItemRequest();
        toEdit.setName("New name");

        assertThrows(NotFoundException.class, () -> {
            itemService.editItem(user.getId() + 1, toEdit, item.getId());
        });
    }

    @Test
    void searchTest() {
        List<ItemResponseDto> dtos = itemService.search(item.getName());
        assertFalse(dtos.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void searchEmptyTextTest(String text) {
        List<ItemResponseDto> dtos = itemService.search(text);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void getAnswerForRequestTest() {
        RequestItemResponseDto request = requestService.makeRequest(user.getId(), Generators.generateRequestItemDto());

        ItemResponseDto item = itemService.createItem(user.getId(),
                ItemRequestDto.builder()
                        .name("Name")
                        .description("Desc")
                        .available(true)
                        .requestId(request.getId())
                        .build());

        AnswerDto answerExpected = AnswerDto.builder()
                .id(item.getId())
                .name(item.getName())
                .ownerId(user.getId())
                .build();

        AnswerDto result = itemService.getItemAnswerForRequests(List.of(request.getId())).get(request.getId()).get(0);

        assertEquals(answerExpected, result);
    }


}
