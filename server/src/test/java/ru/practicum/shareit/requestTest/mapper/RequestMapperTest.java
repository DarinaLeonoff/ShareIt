package ru.practicum.shareit.requestTest.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.request.dto.RequestItemDto;
import ru.practicum.shareit.request.dto.RequestItemResponseDto;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class RequestMapperTest {
    @Autowired
    private RequestMapper requestMapper;

    private RequestItemDto req;
    private RequestItemResponseDto resp;
    private ItemRequest entity;
    private User user;
    private LocalDateTime created;

    @BeforeEach
    void setUp() {
        created = LocalDateTime.now();
        user = Generators.generateUser(1L);
        req = RequestItemDto.builder()
                .description("Test description")
                .build();
        resp = RequestItemResponseDto.builder()
//                .id() - null
                .description(req.getDescription())
                .created(created)
                .build();
        entity = ItemRequest.builder()
//                .id()
                .user(user)
                .description(req.getDescription())
                .created(created)
                .build();
    }

    @Test
    void convertRequestToEntityTest() {
        ItemRequest converted = requestMapper.mapRequestDtoToItemRequest(req, user, created);
        assertTrue(converted.getId() == null, "convertedId is " + converted.getId());
        assertEquals(entity.getDescription(), converted.getDescription());
        assertEquals(entity.getUser(), converted.getUser());
        assertEquals(entity.getCreated(), converted.getCreated());
    }

    @Test
    void convertEntityToResponse() {
        RequestItemResponseDto converted = requestMapper.mapItemRequestToResponse(entity);

        resp.setId(converted.getId());
        assertEquals(resp, converted);
    }

}
