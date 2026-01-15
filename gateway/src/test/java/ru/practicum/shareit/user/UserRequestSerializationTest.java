package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.user.dto.UserRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserRequestSerializationTest {
    private final JacksonTester<UserRequest> json;

    @Test
    void testSerialization() throws IOException {
        UserRequest user = UserRequest.builder()
                .name("Name")
                .email("name@ya.ru")
                .build();

        JsonContent<UserRequest> result = json.write(user);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo(user.getName());
        assertThat(result)
                .extractingJsonPathStringValue("$.email")
                .isEqualTo(user.getEmail());
    }

    @Test
    void testDeserialization() throws IOException {
                String jsonString = "{\n" +
                     "    \"name\": \"Name\",\n" +
                     "    \"email\": \"name@ya.ru\"\n" +
                     "}";

        UserRequest deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getName()).isEqualTo("Name");
        assertThat(deserialized.getEmail()).isEqualTo("name@ya.ru");
    }

}
