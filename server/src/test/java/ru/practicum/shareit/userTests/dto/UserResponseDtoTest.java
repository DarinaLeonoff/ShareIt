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
public class UserResponseDtoTest {
    @Autowired
    private JacksonTester<UserResponseDto> json;

    @Test
    void testSerialization() throws IOException {
        UserResponseDto dto = UserResponseDto.builder()
                .id(1L)
                .name("User")
                .email("user@mail.ru")
                .build();

        JsonContent<UserResponseDto> result = json.write(dto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name")
                .isEqualTo("User");
        assertThat(result).extractingJsonPathStringValue("$.email")
                .isEqualTo("user@mail.ru");
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString = "{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"User\",\n" +
                "    \"email\": \"user@mail.ru\"\n" +
                "}";

        UserResponseDto deserialized = json.parseObject(jsonString);

        assertThat(deserialized.getId()).isEqualTo(1L);
        assertThat(deserialized.getName()).isEqualTo("User");
        assertThat(deserialized.getEmail()).isEqualTo("user@mail.ru");
    }
}
