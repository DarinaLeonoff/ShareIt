package ru.practicum.shareit.ItemTests.model;


import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    @Test
    void testDefaultValues() {
        Item item = new Item();

        // Проверяем значения по умолчанию
        assertEquals(0L, item.getId());
        assertNull(item.getName());
        assertNull(item.getDescription());
        assertTrue(item.isAvailable());
        assertEquals(0L, item.getOwnerId());
    }

    @Test
    void testSettersAndGetters() {
        Item item = Item.builder().build();

        // Устанавливаем значения
        item.setId(123L);
        item.setName("Test Item");
        item.setDescription("Description of test item");
        item.setAvailable(false);
        item.setOwnerId(456L);

        // Проверяем полученные значения
        assertEquals(123L, item.getId());
        assertEquals("Test Item", item.getName());
        assertEquals("Description of test item", item.getDescription());
        assertFalse(item.isAvailable());
        assertEquals(456L, item.getOwnerId());
    }
}
