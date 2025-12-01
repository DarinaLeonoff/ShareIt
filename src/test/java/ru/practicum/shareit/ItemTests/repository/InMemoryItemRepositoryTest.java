package ru.practicum.shareit.ItemTests.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.InMemoryItemRepository;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryItemRepositoryTest {

    private ItemRepository repo;

    @BeforeEach
    private void cleanUp() {
        repo = new InMemoryItemRepository();
    }

    @Test
    void getEmptyListTest() {
        Assertions.assertTrue(repo.getAllUserItems(1).isEmpty());
    }

    @Test
    void createAndGetItemTest() {
        ItemDto dto = Generators.generateDto(0L);
        ItemDto created = repo.createItem(1L, dto);

        List<ItemDto> list = repo.getAllUserItems(1L);
        ItemDto dto1 = repo.getItemById(created.getId());

        assertEquals(1, list.size());
        assertEquals(dto1, list.getFirst());

        assertEquals(dto.getName(), created.getName());
        assertEquals(dto.getDescription(), created.getDescription());
        assertEquals(dto.getAvailable(), created.getAvailable());
    }

    @Test
    void editItemTest() {
        ItemDto created = repo.createItem(1L, Generators.generateDto(1L));

        ItemDto forUpdate = Generators.generateDto(1L);
        forUpdate.setName("New Greate Name");

        ItemDto updated = repo.editItem(1L, forUpdate, created.getId());

        assertEquals(created.getId(), updated.getId());
        assertNotEquals(created.getName(), updated.getName());
        assertEquals(forUpdate.getName(), updated.getName());
        assertEquals(created.getDescription(), updated.getDescription());
        assertEquals(created.getAvailable(), updated.getAvailable());
    }

    @Test
    void searchTest() {
        for (int i = 0;
             i < 10;
             i++) {
            repo.createItem(i, Generators.generateDto(1L));
        }
        String text = "text";
        ItemDto nameTest = Generators.generateDataForSearch(1, text);
        ItemDto descTest = Generators.generateDataForSearch(2, text);
        ItemDto doubleTest = Generators.generateDataForSearch(3, text);

        ItemDto createdNameTest = repo.createItem(1L, nameTest);
        ItemDto createdDescTest = repo.createItem(2L, descTest);
        ItemDto createdDoubleTest = repo.createItem(5L, doubleTest);

        List<ItemDto> search = repo.search(text);

        assertEquals(3, search.size());
        assertTrue(search.contains(createdNameTest));
        assertTrue(search.contains(createdDescTest));
        assertTrue(search.contains(createdDoubleTest));
    }

    @Test
    void searchWithUnableTest() {
        String text = "text";
        List<Long> id = new ArrayList<>();
        for (int i = 0;
             i < 10;
             i++) {
            boolean isText = false;
            ItemDto dto;
            if (i == 2 || i == 8) {
                dto = Generators.generateDataForSearch(1, text);
                isText = true;
            } else {
                dto = Generators.generateDto((long) i);
            }
            if (i > 5) {
                dto.setAvailable(false);
            }
            ItemDto created = repo.createItem(i, dto);
            if (isText) {
                id.add(created.getId());
            }
        }

        List<ItemDto> search = repo.search(text);

        assertEquals(1, search.size());
        assertEquals(id.getFirst(), search.getFirst().getId());
        assertEquals(repo.getItemById(id.getFirst()), search.getFirst());
    }


}
