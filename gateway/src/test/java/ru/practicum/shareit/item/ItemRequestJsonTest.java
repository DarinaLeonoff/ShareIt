package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.item.dto.ItemRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@Import(ItemRequest.class)
public class ItemRequestJsonTest {

    @Autowired
    private JacksonTester<ItemRequest> json;

    @Test
    void testSerialization() throws IOException {
        ItemRequest request = ItemRequest.builder()
                .name("Дрель")
                .description("Аккумуляторная дрель")
                .available(true)
                .requestId(10L)
                .build();

        JsonContent<ItemRequest> content = json.write(request);

        assertThat(content).extractingJsonPathStringValue("$.name")
                .isEqualTo("Дрель");
        assertThat(content).extractingJsonPathStringValue("$.description")
                .isEqualTo("Аккумуляторная дрель");
        assertThat(content).extractingJsonPathBooleanValue("$.available")
                .isEqualTo(true);
        assertThat(content).extractingJsonPathNumberValue("$.requestId")
                .isEqualTo(10);
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

        ItemRequest request = json.parseObject(jsonString);

        assertThat(request.getName()).isEqualTo("Молоток");
        assertThat(request.getDescription()).isEqualTo("Стальной молоток");
        assertThat(request.getAvailable()).isFalse();
        assertThat(request.getRequestId()).isEqualTo(5L);
    }
}