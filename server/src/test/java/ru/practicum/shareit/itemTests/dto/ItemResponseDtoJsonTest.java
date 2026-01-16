package ru.practicum.shareit.itemTests.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = ItemResponseDto.class)
public class ItemResponseDtoJsonTest {

    @Autowired
    private JacksonTester<ItemResponseDto> json;

    @Test
    void testSerialization() throws IOException {
        ItemResponseDto dto = new ItemResponseDto();
        dto.setId(1L);
        dto.setName("Дрель");
        dto.setDescription("Аккумуляторная дрель");
        dto.setAvailable(true);

        JsonContent<ItemResponseDto> content = json.write(dto);

        assertThat(content).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(content).extractingJsonPathStringValue("$.name").isEqualTo("Дрель");
        assertThat(content).extractingJsonPathStringValue("$.description").isEqualTo("Аккумуляторная дрель");
        assertThat(content).extractingJsonPathBooleanValue("$.available").isTrue();
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\n" +
                        "  \"id\": 2,\n" +
                        "  \"name\": \"Молоток\",\n" +
                        "  \"description\": \"Стальной молоток\",\n" +
                        "  \"available\": false\n" +
                        "}";

        ItemResponseDto dto = json.parseObject(jsonString);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getName()).isEqualTo("Молоток");
        assertThat(dto.getDescription()).isEqualTo("Стальной молоток");
        assertThat(dto.getAvailable()).isFalse();
    }
}
