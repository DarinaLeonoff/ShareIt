package ru.practicum.shareit.ItemTests.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.InMemoryItemRepository;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class InMemoryItemRepositoryTest {

    private ItemRepository repo;

    @BeforeEach
    public void cleanUp() {
        repo = new InMemoryItemRepository();
    }

    @Test
    void getEmptyListTest() {
        Assertions.assertTrue(repo.findByOwnerId(1).isEmpty());
    }

    @Test
    void createAndGetItemTest() {
        Item item = Generators.generateItem(0L);
        Item created = repo.saveItem(item);

        List<Item> list = repo.findByOwnerId(1L);
        Item dto1 = repo.findById(created.getId()).orElseThrow(() -> new NotFoundException("Предмет с таким id("+created.getId()+") не найден"));

        assertEquals(1, list.size());
        assertEquals(dto1, list.getFirst());

        assertEquals(item.getName(), created.getName());
        assertEquals(item.getDescription(), created.getDescription());
        assertEquals(item.isAvailable(), created.isAvailable());
    }

    @Test
    void editItemTest() {
        Item created = repo.saveItem(Generators.generateItem(1L));

        Item forUpdate = Generators.generateItem(created.getId());
        forUpdate.setName("New Greate Name");

        Item updated = repo.editItem(forUpdate);

        assertEquals(created.getId(), updated.getId());
        assertNotEquals(created.getName(), updated.getName());
        assertEquals(forUpdate.getName(), updated.getName());
        assertEquals(created.getDescription(), updated.getDescription());
        assertEquals(created.isAvailable(), updated.isAvailable());
    }

    @Test
    void searchByTextTest() {
        for (int i = 0;
             i < 10;
             i++) {
            repo.saveItem(Generators.generateItem(1L));
        }
        String text = "text";
        Item nameTest = Generators.generateItemForSearch(1, text);
        Item descTest = Generators.generateItemForSearch(2, text);
        Item doubleTest = Generators.generateItemForSearch(3, text);

        Item createdNameTest = repo.saveItem(nameTest);
        Item createdDescTest = repo.saveItem(descTest);
        Item createdDoubleTest = repo.saveItem(doubleTest);

        List<Item> search = repo.searchByText(text);

        assertEquals(3, search.size());
        assertTrue(search.contains(createdNameTest));
        assertTrue(search.contains(createdDescTest));
        assertTrue(search.contains(createdDoubleTest));
    }

    @Test
    void searchByTextWithUnableTest() {
        String text = "text";
        List<Long> id = new ArrayList<>();
        for (int i = 0;
             i < 10;
             i++) {
            boolean isText = false;
            Item item;
            if (i == 2 || i == 8) {
                item = Generators.generateItemForSearch(1, text);
                isText = true;
            } else {
                item = Generators.generateItem((long) i);
            }
            if (i > 5) {
                item.setAvailable(false);
            }
            Item created = repo.saveItem(item);
            if (isText) {
                id.add(created.getId());
            }
        }

        List<Item> search = repo.searchByText(text);

        assertEquals(1, search.size());
        assertEquals(id.getFirst(), search.getFirst().getId());
        assertEquals(repo.findById(id.getFirst()).get(), search.getFirst());
    }


}
