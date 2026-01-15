package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class BookItemRequestDto {
    private long itemId;
    @FutureOrPresent
    private LocalDateTime start;

    @Future
    private LocalDateTime end;

//    @AssertTrue(message = "Дата окончания должна быть позже даты начала")
//    public boolean isValidDateRange() {
//        return start.isBefore(end);
//    }
}

