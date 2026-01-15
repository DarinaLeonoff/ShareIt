package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.ItemUpdateRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemUpdateRequestSerializationTest {
    private final JacksonTester<ItemUpdateRequest> json;

    @Test
    void testSerialization() throws IOException {
        ItemUpdateRequest updateItem = ItemUpdateRequest.builder()
                .name("Updated")
                .description("Description")
                .available(true)
                .build();

        JsonContent<ItemUpdateRequest> result = json.write(updateItem);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(updateItem.getName());
        assertThat(result)
                .extractingJsonPathStringValue("$.description")
                .isEqualTo(updateItem.getDescription());
        assertThat(result)
                .extractingJsonPathBooleanValue("$.available")
                .isEqualTo(true);
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString = "{\n" +
                "    \"name\": \"Update\",\n" +
                "    \"description\": \"Description\",\n" +
                "    \"available\": true\n" +
                "}";

        ItemUpdateRequest deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getName()).isEqualTo("Update");
        assertThat(deserialized.getDescription()).isEqualTo("Description");
        assertThat(deserialized.getAvailable()).isTrue();
    }

    @Test
    void testDeserializationWithNameOnly() throws IOException {
        String jsonString = "{\n" +
                "    \"name\": \"Update\"\n" +
                "}";

        ItemUpdateRequest deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getName()).isEqualTo("Update");
        assertThat(deserialized.getDescription()).isNull();
        assertThat(deserialized.getAvailable()).isNull();
    }

    @Test
    void testDeserializationWithDescriptionOnly() throws IOException {
        String jsonString = "{\n" +
                "    \"description\": \"Description\"\n" +
                "}";

        ItemUpdateRequest deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getName()).isNull();
        assertThat(deserialized.getDescription()).isEqualTo("Description");
        assertThat(deserialized.getAvailable()).isNull();
    }

    @Test
    void testDeserializationWithAvailableOnly() throws IOException {
        String jsonString = "{\n" +
                "    \"available\": true\n" +
                "}";

        ItemUpdateRequest deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getName()).isNull();
        assertThat(deserialized.getDescription()).isNull();
        assertThat(deserialized.getAvailable()).isTrue();
    }
}
