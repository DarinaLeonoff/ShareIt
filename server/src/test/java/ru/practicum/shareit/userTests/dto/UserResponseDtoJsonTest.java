package ru.practicum.shareit.userTests.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.user.dto.UserResponseDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = UserResponseDto.class)
public class UserResponseDtoJsonTest {
    @Autowired
    private JacksonTester<UserResponseDto> json;

    @Test
    void testSerialization() throws IOException {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(1L);
        dto.setName("Дарья");
        dto.setEmail("darya@mail.ru");

        JsonContent<UserResponseDto> content = json.write(dto);

        assertThat(content).extractingJsonPathNumberValue("$.id")
                .isEqualTo(1);
        assertThat(content).extractingJsonPathStringValue("$.name")
                .isEqualTo("Дарья");
        assertThat(content).extractingJsonPathStringValue("$.email")
                .isEqualTo("darya@mail.ru");
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\n" +
                        "  \"id\": 2,\n" +
                        "  \"name\": \"Алексей\",\n" +
                        "  \"email\": \"alex@mail.ru\"\n" +
                        "}";

        UserResponseDto dto = json.parseObject(jsonString);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getName()).isEqualTo("Алексей");
        assertThat(dto.getEmail()).isEqualTo("alex@mail.ru");
    }
}
