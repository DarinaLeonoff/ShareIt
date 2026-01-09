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

    @Mock
    private RequestRepository requestRepository;
    @Mock
    private DbUserRepository userRepository;

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
        RequestItemDto req = Generators.generateRequestItemDto();
        RequestItemResponseDto res = requestService.makeRequest(user.getId(), req);

        RequestItemDto req2 = Generators.generateRequestItemDto();
        RequestItemResponseDto res2 = requestService.makeRequest(user.getId(), req2);

        UserResponseDto user2 = userService.createUser(Generators.generateUserRequestDto());
        requestService.makeRequest(user2.getId(), Generators.generateRequestItemDto());

        List<RequestItemResponseDto> result = requestService.getUserRequest(user.getId());
        assertEquals(2, result.size());
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
