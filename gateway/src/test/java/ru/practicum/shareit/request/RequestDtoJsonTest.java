package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.request.dto.RequestDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@Import(RequestDto.class)
public class RequestDtoJsonTest {

    @Autowired
    private JacksonTester<RequestDto> json;

    @Test
    void testSerialization() throws IOException {
        RequestDto dto = new RequestDto();
        dto.setDescription("Нужна лестница");

        JsonContent<RequestDto> content = json.write(dto);

        assertThat(content).extractingJsonPathStringValue("$.description")
                .isEqualTo("Нужна лестница");
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\n" +
                        "  \"description\": \"Ищу перфоратор\"\n" +
                        "}";

        RequestDto dto = json.parseObject(jsonString);

        assertThat(dto.getDescription())
                .isEqualTo("Ищу перфоратор");
    }
}
