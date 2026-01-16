package ru.practicum.shareit.userTests.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.context.ContextConfiguration;
import ru.practicum.shareit.user.dto.UserRequestDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@ContextConfiguration(classes = UserRequestDto.class)
public class UserRequestDtoJsonTest {

    @Autowired
    private JacksonTester<UserRequestDto> json;

    @Test
    void testSerialization() throws IOException {
        UserRequestDto dto = UserRequestDto.builder()
                .name("Дарья")
                .email("darya@mail.ru")
                .build();

        JsonContent<UserRequestDto> content = json.write(dto);

        assertThat(content).extractingJsonPathStringValue("$.name").isEqualTo("Дарья");
        assertThat(content).extractingJsonPathStringValue("$.email").isEqualTo("darya@mail.ru");
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\n" +
                        "  \"name\": \"Алексей\",\n" +
                        "  \"email\": \"alex@mail.ru\"\n" +
                        "}";

        UserRequestDto dto = json.parseObject(jsonString);

        assertThat(dto.getName()).isEqualTo("Алексей");
        assertThat(dto.getEmail()).isEqualTo("alex@mail.ru");
    }
}
