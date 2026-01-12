package ru.practicum.shareit.requestTest.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.DbUserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RequestRepositoryTest {
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private DbUserRepository userRepository;

    private User user;
    private ItemRequest request;

    @BeforeEach
    void setUp() {
        user = userRepository.save(Generators.generateUser(1L));
        request = ItemRequest.builder()
                .description("test")
                .user(user)
                .created(LocalDateTime.now())
                .build();
    }

    @Test
    void saveRequestTest() {
        ItemRequest res = requestRepository.save(request);

        assertEquals(request.getDescription(), res.getDescription());
        assertEquals(request.getUser(), res.getUser());
        assertEquals(request.getCreated(), res.getCreated());
    }

    @Test
    void overwriteWhileSavingTest() {
        ItemRequest res = requestRepository.save(request);
        ItemRequest request2 = ItemRequest.builder()
                .description("test2")
                .user(user)
                .created(LocalDateTime.now())
                .build();
        ItemRequest res2 = requestRepository.save(request2);

        assertEquals(request.getDescription(), res.getDescription());
        assertEquals(request.getUser(), res.getUser());
        assertEquals(request.getCreated(), res.getCreated());

        assertNotEquals(res.getId(), res2.getId());
    }

    @Test
    void getByUserIdTest() {
        ItemRequest res1 = requestRepository.save(Generators.generateRequestItem(user));
        ItemRequest res2 = requestRepository.save(Generators.generateRequestItem(user));

        List<ItemRequest> result = requestRepository.findAllByUser_IdOrderByCreatedDesc(user.getId());

        assertEquals(2, result.size());
        assertTrue(result.get(0).getCreated().isAfter(result.get(1).getCreated()));
    }

    @Test
    void getAllTest() {
        ItemRequest res1 = requestRepository.save(Generators.generateRequestItem(user));
        ItemRequest res2 = requestRepository.save(Generators.generateRequestItem(user));
        ItemRequest res3 = requestRepository.save(Generators.generateRequestItem(user));

        List<ItemRequest> result = requestRepository.findAllByOrderByCreatedDesc();
        assertNotEquals(res1.getId(), res2.getId());
        assertNotEquals(res2.getId(), res3.getId());

        assertEquals(3, result.size());
        assertTrue(result.get(0).getCreated().isAfter(result.get(1).getCreated()));
    }

    @Test
    void getByIdTest() {
        ItemRequest res1 = requestRepository.save(Generators.generateRequestItem(user));
        ItemRequest res2 = requestRepository.save(Generators.generateRequestItem(user));
        ItemRequest res3 = requestRepository.save(Generators.generateRequestItem(user));

        ItemRequest result = requestRepository.findById(res3.getId()).get();
        assertEquals(res3, result);
    }
}
