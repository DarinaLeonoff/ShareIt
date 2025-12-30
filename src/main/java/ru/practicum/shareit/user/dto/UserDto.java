package ru.practicum.shareit.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Valid
public class UserDto {
    private long id;

    @NotNull
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @NotNull
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;
}
