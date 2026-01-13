package ru.practicum.shareit.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.user.dto.UserRequestDto;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User mapDtoToUser(UserRequestDto dto);

    UserResponseDto mapUserToResponseDto(User user);

    User mapResponseToUser(UserResponseDto dto);

    default User editUser(User user, UserRequestDto userDto) {
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

