package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.model.User;

public class UserMapperUtils {
    public static User editUser(User user, UserRequestDto userDto) {
        String email = userDto.getEmail();
        if (email != null && !email.isBlank()
                && !email.equals(user.getEmail())) {
            user.setEmail(email);
        }

        String name = userDto.getName();
        if (name != null && !name.isBlank() && !name.equals(user.getName())) {
            user.setName(name);
        }

        return user;
    }
}
