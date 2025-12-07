package ru.practicum.shareit.ItemTests.model;


import org.junit.jupiter.api.Test;
import ru.practicum.shareit.Generators;
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
        Item item = new Item();

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

    @Test
    void testEqualsAndHashCode() {
        Item item1 = Generators.generateItem(1L);
        Item item2 = Generators.generateItem(1L);
        Item item3 = Generators.generateItem(2L);

        assertTrue(item1.equals(item1)); // Рефлексивность
        assertTrue(item1.equals(item2)); // Симметричность
        assertFalse(item1.equals(item3)); // Разные объекты
        assertFalse(item1.equals(null));

        assertEquals(item1.hashCode(), item2.hashCode());
        assertNotEquals(item1.hashCode(), item3.hashCode());
    }

    @Test
    void testToString() {
        Item item = Generators.generateItem(1L);

        String expected = "Item(id=1, name=Test, description=Desc, available=true, " + "ownerId=100)";
        assertEquals(expected, item.toString());
    }


}
