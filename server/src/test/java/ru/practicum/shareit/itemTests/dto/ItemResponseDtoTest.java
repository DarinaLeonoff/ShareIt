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
public class ItemResponseDtoTest {
    @Autowired
    private JacksonTester<ItemResponseDto> json;

    @Test
    void testSerialization() throws IOException {
        ItemResponseDto item = ItemResponseDto.builder()
                .id(1L)
                .name("Item")
                .description("Description")
                .available(true)
                .build();

        JsonContent<ItemResponseDto> result = json.write(item);
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(item.getName());
        assertThat(result)
                .extractingJsonPathStringValue("$.description")
                .isEqualTo(item.getDescription());
        assertThat(result)
                .extractingJsonPathBooleanValue("$.available")
                .isEqualTo(item.getAvailable());
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Item\",\n" +
                "    \"description\": \"description\",\n" +
                "    \"available\": true\n" +
                "}";

        ItemResponseDto deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getId()).isEqualTo(1);
        assertThat(deserialized.getName()).isEqualTo("Item");
        assertThat(deserialized.getDescription()).isEqualTo("description");
        assertThat(deserialized.getAvailable()).isTrue();
    }
}
