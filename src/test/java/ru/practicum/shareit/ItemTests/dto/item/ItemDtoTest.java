package ru.practicum.shareit.ItemTests.dto.item;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.Generators;
import ru.practicum.shareit.item.dto.item.ItemDto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemDtoTest {
    @Autowired
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void validationTest() {
        ItemDto dto = Generators.generateDto(1L);

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(dto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void validationNameTest(String name) {
        ItemDto dto = Generators.generateDto(1L);
        dto.setName(name);

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void validationDescTest(String desc) {
        ItemDto dto = Generators.generateDto(1L);
        dto.setDescription(desc);

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @ParameterizedTest
    @NullSource
    public void validationAvailableTest(Boolean b) {
        ItemDto dto = Generators.generateDto(1L);
        dto.setAvailable(b);

        Set<ConstraintViolation<ItemDto>> violations = validator.validate(dto);
        Assertions.assertFalse(violations.isEmpty());
    }

    //getter, setter, hash and toString tests

    @Test
    void testDefaultValues() {
        ItemDto item = new ItemDto();

        // Проверяем значения по умолчанию
        assertEquals(0L, item.getId());
        assertNull(item.getName());
        assertNull(item.getDescription());
        assertNull(item.getAvailable());
    }

    @Test
    void testSettersAndGetters() {
        ItemDto item = new ItemDto();

        // Устанавливаем значения
        item.setId(123L);
        item.setName("Test Item");
        item.setDescription("Description of test item");
        item.setAvailable(false);

        // Проверяем полученные значения
        assertEquals(123L, item.getId());
        assertEquals("Test Item", item.getName());
        assertEquals("Description of test item", item.getDescription());
        assertFalse(item.getAvailable());
    }

    @Test
    void testEqualsAndHashCode() {
        ItemDto item1 = Generators.generateDto(1L);
        ItemDto item2 = Generators.generateDto(1L);
        ItemDto item3 = Generators.generateDto(2L);

        assertTrue(item1.equals(item1)); // Рефлексивность
        assertTrue(item1.equals(item2)); // Симметричность
        assertFalse(item1.equals(item3)); // Разные объекты
        assertFalse(item1.equals(null));

        assertEquals(item1.hashCode(), item2.hashCode());
        assertNotEquals(item1.hashCode(), item3.hashCode());
    }

    @Test
    void testToString() {
        ItemDto item = Generators.generateDto(1L);

        String expected = "ItemDto(id=1, name=Dto test name, description=Dto test desc, available=true)";
        assertEquals(expected, item.toString());
    }
}
