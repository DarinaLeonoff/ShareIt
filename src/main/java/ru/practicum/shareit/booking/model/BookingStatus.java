package ru.practicum.shareit.booking.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BookingStatus {
    WAITING(1), APPROVED(2), REJECTED(3);

    private int code;

    BookingStatus(int code) {
        this.code = code;
    }

    public static BookingStatus getByCode(int code) {
        return Arrays.stream(values())
                .filter(status -> status.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Неверный код статуса бронирования: " + code));
    }
}
