package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.item.dto.NewComment;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NewCommentSerializationTest {

    private final JacksonTester<NewComment> json;

    @Test
    void testSerialization() throws IOException {
        NewComment comment = NewComment.builder()
                .text("Comment")
                .build();

        JsonContent<NewComment> result = json.write(comment);
        assertThat(result).extractingJsonPathStringValue("$.text").isEqualTo(comment.getText());
    }

    @Test
    void testDeserialization() throws IOException {
        String jsonString = "{\n" +
                "    \"text\": \"Comment\"\n" +
                "}";

        NewComment deserialized = json.parseObject(jsonString);
        assertThat(deserialized.getText()).isEqualTo("Comment");
    }
}
