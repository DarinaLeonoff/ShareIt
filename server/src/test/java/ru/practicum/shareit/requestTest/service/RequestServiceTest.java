package ru.practicum.shareit.requestTest.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.RequestItemDto;
import ru.practicum.shareit.request.dto.RequestItemResponseDto;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.repository.DbUserRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
public class RequestServiceTest {
    @Autowired
    private RequestService requestService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @Mock
    private RequestRepository requestRepository;
    @Mock
    private DbUserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;

    private UserResponseDto user;

    @BeforeEach
    void setUp() {
        user = userService.createUser(Generators.generateUserRequestDto());
    }

    @Test
    void makeRequestTest() {
        RequestItemDto req = Generators.generateRequestItemDto();
        RequestItemResponseDto res = requestService.makeRequest(user.getId(), req);

        assertEquals(req.getDescription(), res.getDescription());
    }

    @Test
    void getUserRequestTest() {
        RequestItemResponseDto res = requestService.makeRequest(user.getId(), Generators.generateRequestItemDto());
        ItemResponseDto item1 = itemService.createItem(user.getId(), Generators.generateItemRequest(res.getId()));

        RequestItemResponseDto res2 = requestService.makeRequest(user.getId(), Generators.generateRequestItemDto());
        ItemResponseDto item2 = itemService.createItem(user.getId(), Generators.generateItemRequest(res2.getId()));

        UserResponseDto user2 = userService.createUser(Generators.generateUserRequestDto());
        requestService.makeRequest(user2.getId(), Generators.generateRequestItemDto());

        List<RequestItemResponseDto> result = requestService.getUserRequest(user.getId());
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getItems().size());
        assertEquals(1, result.get(1).getItems().size());

    }

    @Test
    void getAllTest() {
        UserResponseDto user2 = userService.createUser(Generators.generateUserRequestDto());
        RequestItemResponseDto res1 = requestService.makeRequest(user.getId(), Generators.generateRequestItemDto());
        RequestItemResponseDto res2 = requestService.makeRequest(user.getId(), Generators.generateRequestItemDto());
        RequestItemResponseDto res3 = requestService.makeRequest(user2.getId(), Generators.generateRequestItemDto());

        List<RequestItemResponseDto> result = requestService.getAllRequest();
        assertNotEquals(res1.getId(), res2.getId());
        assertNotEquals(res2.getId(), res3.getId());
        assertEquals(3, result.size());
    }

    @Test
    void getByIdTest() {
        RequestItemResponseDto res1 = requestService.makeRequest(user.getId(), Generators.generateRequestItemDto());
        RequestItemResponseDto res2 = requestService.makeRequest(user.getId(), Generators.generateRequestItemDto());
        RequestItemResponseDto res3 = requestService.makeRequest(user.getId(), Generators.generateRequestItemDto());

        RequestItemResponseDto result = requestService.getRequest(res3.getId());
        assertEquals(res3, result);
    }
}
