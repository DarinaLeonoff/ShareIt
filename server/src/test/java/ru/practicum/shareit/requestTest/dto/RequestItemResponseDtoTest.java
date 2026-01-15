package ru.practicum.shareit.requestTest.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.request.dto.RequestItemResponseDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = RequestItemResponseDto.class)
public class RequestItemResponseDtoTest {
    @Autowired
    private JacksonTester<RequestItemResponseDto> json;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS");


    @Test
    void testSerialization() throws IOException {
        LocalDateTime time = LocalDateTime.now();
        RequestItemResponseDto resp = RequestItemResponseDto.builder()
                .id(1L)
                .description("Desc")
                .created(time)
                .build();
        JsonContent<RequestItemResponseDto> result = json.write(resp);
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result)
                .extractingJsonPathStringValue("$.description")
                .isEqualTo(resp.getDescription());
        assertThat(result).extractingJsonPathStringValue("$.created").isEqualTo(time.format(formatter));
        assertThat(result).extractingJsonPathArrayValue("$.items").isEmpty();
    }

    @Test
    void testDeserialization() throws IOException {
        LocalDateTime time = LocalDateTime.now();

        String jsonString = "{\n" +
                "    \"id\": 1,\n" +
                "    \"description\": \"description\",\n" +
                "    \"created\": \"" + time + "\",\n" +
                "    \"items\": []\n" +
                "}";

        RequestItemResponseDto deserialized = json.parseObject(jsonString);

        assertThat(deserialized.getId()).isEqualTo(1L);
        assertThat(deserialized.getDescription()).isEqualTo("description");
        assertThat(deserialized.getCreated()).isEqualTo(time);
        assertThat(deserialized.getItems()).isNotNull();
        assertThat(deserialized.getItems()).isEmpty();
    }
}
