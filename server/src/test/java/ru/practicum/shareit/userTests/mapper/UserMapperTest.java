package ru.practicum.shareit.userTests.mapper;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void convertUserRequestDtoToUserTest() {
        UserRequestDto dto = UserRequestDto.builder()
                .name("Test name")
                .email("test@ya.ru")
                .build();
        User user = userMapper.mapDtoToUser(dto);

        assertEquals(dto.getName(), user.getName());
        assertEquals(dto.getEmail(), user.getEmail());
        assertEquals(0, user.getId());
    }

    @Test
    void convertUserToResponseTest() {
        User user = User.builder()
                .id(1L)
                .name("Test")
                .email("test@ya.ru")
                .build();
        UserResponseDto expected = UserResponseDto.builder()
                .id(1L)
                .name("Test")
                .email("test@ya.ru")
                .build();

        UserResponseDto result = userMapper.mapUserToResponseDto(user);

        assertEquals(expected, result);
    }

    @Test
    void convertResponseToUserTest() {
        UserResponseDto user = UserResponseDto.builder()
                .id(1L)
                .name("Test")
                .email("test@ya.ru")
                .build();
        User expected = User.builder()
                .id(1L)
                .name("Test")
                .email("test@ya.ru")
                .build();

        User result = userMapper.mapResponseToUser(user);
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getEmail(), result.getEmail());
        assertEquals(expected.getId(), result.getId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void editUserNameTest(String name) {
        User user = User.builder()
                .id(1L)
                .name("Test")
                .email("test@ya.ru")
                .build();

        UserRequestDto dto = UserRequestDto.builder()
                .name(name)
                .email("testTest@ya.ru")
                .build();

        User result = userMapper.editUser(user, dto);

        assertEquals(user.getId(), result.getId());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(user.getName(), result.getName());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void editUserEmailTest(String email) {
        User user = User.builder()
                .id(1L)
                .name("Test")
                .email("test@ya.ru")
                .build();

        UserRequestDto dto = UserRequestDto.builder()
                .name("Name")
                .email(email)
                .build();

        User result = userMapper.editUser(user, dto);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(dto.getName(), result.getName());
    }
}
