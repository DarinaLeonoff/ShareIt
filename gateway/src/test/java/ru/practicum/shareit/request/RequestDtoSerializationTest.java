package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.request.dto.RequestDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestDtoSerializationTest {
    private final JacksonTester<RequestDto> json;

    @Test
    void testSerialization() throws IOException {
        RequestDto request = RequestDto.builder()
                .description("Description")
                .build();

        JsonContent<RequestDto> result = json.write(request);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("Description");
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString = "{\n" +
                "    \"description\": \"Description\"\n" +
                "}";
        RequestDto deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getDescription()).isEqualTo("Description");
    }
}
