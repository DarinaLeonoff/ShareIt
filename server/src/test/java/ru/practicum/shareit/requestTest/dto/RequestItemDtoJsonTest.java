package ru.practicum.shareit.requestTest.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.request.dto.RequestItemDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = RequestItemDto.class)
public class RequestItemDtoJsonTest {

    @Autowired
    private JacksonTester<RequestItemDto> json;

    @Test
    void testSerialization() throws IOException {
        RequestItemDto dto = RequestItemDto.builder()
                .description("Нужна лестница")
                .build();

        JsonContent<RequestItemDto> content = json.write(dto);

        assertThat(content).extractingJsonPathStringValue("$.description")
                .isEqualTo("Нужна лестница");
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\n" +
                        "  \"description\": \"Ищу перфоратор\"\n" +
                        "}";

        RequestItemDto dto = json.parseObject(jsonString);

        assertThat(dto.getDescription()).isEqualTo("Ищу перфоратор");
    }
}
