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
public class RequestItemDtoTest {
    @Autowired
    private JacksonTester<RequestItemDto> json;

    @Test
    void testSerialization() throws IOException {
        RequestItemDto req = RequestItemDto.builder().description("Description").build();
        JsonContent<RequestItemDto> result = json.write(req);
        assertThat(result)
                .extractingJsonPathStringValue("$.description")
                .isEqualTo(req.getDescription());
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString = "{\n" +
                "    \"description\": \"description\"\n" +
                "}";


        RequestItemDto deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getDescription()).isEqualTo("description");
    }
}
