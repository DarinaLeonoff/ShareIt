package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.user.dto.UserUpdateRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserUpdateRequestSerializationTest {
    private final JacksonTester<UserUpdateRequest> json;

    @Test
    void testSerialization() throws IOException {
        UserUpdateRequest user = new UserUpdateRequest();
        user.setName("Name");
        user.setEmail("name@ya.ru");

        JsonContent<UserUpdateRequest> result = json.write(user);
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

        UserUpdateRequest deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getName()).isEqualTo("Name");
        assertThat(deserialized.getEmail()).isEqualTo("name@ya.ru");
    }

    @Test
    void testDeserializationNameOnly() throws IOException {
        String jsonString = "{\n" +
                "    \"name\": \"Name\"\n" +
                "}";

        UserUpdateRequest deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getName()).isEqualTo("Name");
        assertThat(deserialized.getEmail()).isNull();
    }

    @Test
    void testDeserializationEmailOnly() throws IOException {
        String jsonString = "{\n" +
                "    \"email\": \"name@ya.ru\"\n" +
                "}";

        UserUpdateRequest deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getName()).isNull();
        assertThat(deserialized.getEmail()).isEqualTo("name@ya.ru");
    }

}
