package ru.practicum.shareit.itemTests.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.item.dto.item.ItemRequestDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = ItemRequestDto.class)
public class ItemRequestDtoJsonTest {

    @Autowired
    private JacksonTester<ItemRequestDto> json;

    @Test
    void testSerialization() throws IOException {
        ItemRequestDto dto = ItemRequestDto.builder()
                .name("Дрель")
                .description("Аккумуляторная дрель")
                .available(true)
                .requestId(10L)
                .build();

        JsonContent<ItemRequestDto> content = json.write(dto);

        assertThat(content).extractingJsonPathStringValue("$.name").isEqualTo("Дрель");
        assertThat(content).extractingJsonPathStringValue("$.description").isEqualTo("Аккумуляторная дрель");
        assertThat(content).extractingJsonPathBooleanValue("$.available").isTrue();
        assertThat(content).extractingJsonPathNumberValue("$.requestId").isEqualTo(10);
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\n" +
                        "  \"name\": \"Молоток\",\n" +
                        "  \"description\": \"Стальной молоток\",\n" +
                        "  \"available\": false,\n" +
                        "  \"requestId\": 5\n" +
                        "}";

        ItemRequestDto dto = json.parseObject(jsonString);

        assertThat(dto.getName()).isEqualTo("Молоток");
        assertThat(dto.getDescription()).isEqualTo("Стальной молоток");
        assertThat(dto.getAvailable()).isFalse();
        assertThat(dto.getRequestId()).isEqualTo(5L);
    }
}
