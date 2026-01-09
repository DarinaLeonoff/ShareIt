package ru.practicum.shareit.itemTests.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.DbItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.DbUserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ItemDbRepositoryTest {
    @Autowired
    private DbItemRepository itemRepository;
    @Autowired
    private DbUserRepository userRepository;

    private User testUser;
    private Item testItem;

    @BeforeEach
    void setUp() {
        testUser = userRepository.save(Generators.generateUser(1L));
        testItem = itemRepository.save(Generators.generateItem(1L, testUser.getId()));
    }

    @Test
    void testSaveItem() {
        // Проверка результата
        Optional<Item> result = itemRepository.findById(testItem.getId());
        assertTrue(result.isPresent());
    }

    @Test
    void testGetByExistId() {
        Optional<Item> result = itemRepository.findById(testItem.getId());

        assertTrue(result.isPresent());
        assertEquals(result.get(), testItem);
    }

    @Test
    void testGetByNotExistId() {
        Optional<Item> result = itemRepository.findById(200L);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetByExistOwner() {
        List<Item> items = itemRepository.findByOwnerId(testUser.getId());

        assertFalse(items.isEmpty());
        assertEquals(items.getFirst(), testItem);
    }

    @Test
    void testGetByNotExistOwner() {
        List<Item> items = itemRepository.findByOwnerId(20L);

        assertTrue(items.isEmpty());
    }

    @Test
    void testSearchByTextWithMatch() {
        fillDbForSearch();

        List<Item> items = itemRepository.searchByText("match");

        assertEquals(3, items.size());
    }

    @Test
    void testSearchByTextWithoutMatch() {
        fillDbForSearch();

        List<Item> items = itemRepository.searchByText("Yandex");

        assertTrue(items.isEmpty());
    }

    private void fillDbForSearch() {
        itemRepository.save(Item.builder()
                .name("Test")
                .description("Desc")
                .available(true)
                .ownerId(testUser.getId())
                .build());
        itemRepository.save(Item.builder()
                .name("Name")
                .description("Some text")
                .available(true)
                .ownerId(testUser.getId())
                .build());
        itemRepository.save(Item.builder()
                .name("Match")
                .description("Match")
                .available(true)
                .ownerId(testUser.getId())
                .build());
        itemRepository.save(Item.builder()
                .name("Match")
                .description("Desc")
                .available(true)
                .ownerId(testUser.getId())
                .build());
        itemRepository.save(Item.builder()
                .name("Test")
                .description("Match")
                .available(true)
                .ownerId(testUser.getId())
                .build());
    }

}
