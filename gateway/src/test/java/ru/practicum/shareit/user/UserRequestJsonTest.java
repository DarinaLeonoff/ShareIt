package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.context.annotation.Import;
import ru.practicum.shareit.user.dto.UserRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@Import(UserRequest.class)
public class UserRequestJsonTest {

    @Autowired
    private JacksonTester<UserRequest> json;

    @Test
    void testSerialization() throws IOException {
        UserRequest request = new UserRequest();
        request.setName("Дарья");
        request.setEmail("darya@mail.ru");

        JsonContent<UserRequest> content = json.write(request);

        assertThat(content).extractingJsonPathStringValue("$.name")
                .isEqualTo("Дарья");
        assertThat(content).extractingJsonPathStringValue("$.email")
                .isEqualTo("darya@mail.ru");
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString =
                "{\n" +
                        "  \"name\": \"Алексей\",\n" +
                        "  \"email\": \"alex@mail.ru\"\n" +
                        "}";

        UserRequest request = json.parseObject(jsonString);

        assertThat(request.getName()).isEqualTo("Алексей");
        assertThat(request.getEmail()).isEqualTo("alex@mail.ru");
    }
}

