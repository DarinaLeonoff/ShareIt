package ru.practicum.shareit.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ErrorResponse {
    @Getter
    private String error, description;
}
