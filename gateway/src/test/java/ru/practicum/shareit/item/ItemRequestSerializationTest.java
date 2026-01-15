package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestSerializationTest {

    private final JacksonTester<ItemRequest> json;

    @Test
    void testSerialization() throws IOException {
        ItemRequest item = ItemRequest.builder()
                .name("Item")
                .description("Description")
                .available(true)
                .build();

        JsonContent<ItemRequest> result = json.write(item);
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
                "    \"name\": \"Item\",\n" +
                "    \"description\": \"description\",\n" +
                "    \"available\": true\n" +
                "}";

        ItemRequest deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getName()).isEqualTo("Item");
        assertThat(deserialized.getDescription()).isEqualTo("description");
        assertThat(deserialized.getAvailable()).isTrue();
    }
}
