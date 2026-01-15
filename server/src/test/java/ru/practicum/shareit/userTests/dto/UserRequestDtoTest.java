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
public class UserRequestDtoTest {
    @Autowired
    private JacksonTester<UserRequestDto> json;

    @Test
    void testSerialization() throws IOException {
        UserRequestDto dto = UserRequestDto.builder()
                .name("User")
                .email("user@mail.ru")
                .build();

        JsonContent<UserRequestDto> result = json.write(dto);

        assertThat(result).extractingJsonPathStringValue("$.name")
                .isEqualTo("User");
        assertThat(result).extractingJsonPathStringValue("$.email")
                .isEqualTo("user@mail.ru");
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString = "{\n" +
                "    \"name\": \"User\",\n" +
                "    \"email\": \"user@mail.ru\"\n" +
                "}";


        UserRequestDto deserialized = json.parseObject(jsonString);

        assertThat(deserialized.getName()).isEqualTo("User");
        assertThat(deserialized.getEmail()).isEqualTo("user@mail.ru");
    }
}
