package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.WrongRequestException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final BookingMapper mapper;
    private final UserMapper userMapper;
    private final ItemMapper itemMapper;

    @Override
    public BookingResponseDto makeBooking(BookingRequestDto dto, long userId) {
        UserDto user = userService.getUser(userId);
        Item item = itemMapper.mapToItem(itemService.getItemById(dto.getItemId()));
        if (!item.isAvailable()){
            throw new WrongRequestException("Объект не доступен для бронирования.");
        }

        Booking booking = mapper.mapRequestDtoToBooking(dto);
        booking.setBooker(userMapper.mapDtoToUser(user));
        booking.setItem(item);
        booking.setStatus(BookingStatus.WAITING);

        return mapper.mapBookingToResponseDto(bookingRepository.save(booking));
    }

    public BookingResponseDto approveBooking(long userId, long bookingId, boolean approved){
        Booking booking = bookingRepository.findById(bookingId);
        Item item = booking.getItem();
        if(userId != item.getOwnerId()){
            throw new WrongRequestException("Пользователь не является собственником и не может подтверждать бронь");
        }

        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return mapper.mapBookingToResponseDto(bookingRepository.save(booking));
    }

    public BookingResponseDto getBookingById(long userId, long bookingId){
        Booking booking = bookingRepository.findById(bookingId);
        if(booking.getBooker().getId() != userId && booking.getItem().getOwnerId() != userId){
            throw new WrongRequestException("У пользователя нет прав доступа к информации о бронировании.");
        }
        return mapper.mapBookingToResponseDto(booking);
    }

    @Override
    public List<BookingResponseDto> getAllUserBooking(long userId, BookingState state) {
        UserDto user = userService.getUser(userId);
        return switch (state){
            case PAST -> getPast(userId);
            case FUTURE -> getFuture(userId);
            case CURRENT -> getCurrent(userId);
            case WAITING -> getWaiting(userId);
            case REJECTED -> getRejected(userId);
            default -> getAll(userId);
        };
    }

    private List<BookingResponseDto> getPast(long userId){
        LocalDateTime cur = LocalDateTime.now();
        return bookingRepository.findByBookerId(userId).stream()
                .filter(booking -> booking.getStart().isBefore(cur)
                        && booking.getEnd().isBefore(cur)
                        && booking.getStatus().equals(BookingStatus.APPROVED))
                .map(mapper::mapBookingToResponseDto).toList();

    }

    private List<BookingResponseDto> getFuture(long userId){
        LocalDateTime cur = LocalDateTime.now();
        return bookingRepository.findByBookerId(userId).stream()
                .filter(booking -> booking.getEnd().isAfter(cur)
                        && booking.getStatus().equals(BookingStatus.APPROVED))
        .map(mapper::mapBookingToResponseDto).toList();
    }

    private List<BookingResponseDto> getCurrent(long userId){
        LocalDateTime cur = LocalDateTime.now();
        return bookingRepository.findByBookerId(userId).stream()
                .filter(booking -> booking.getStart().isBefore(cur)
                        && booking.getEnd().isAfter(cur)
                        && booking.getStatus().equals(BookingStatus.APPROVED))
                .map(mapper::mapBookingToResponseDto).toList();
    }

    private List<BookingResponseDto> getWaiting(long userId){
        return bookingRepository.findByBookerId(userId).stream().filter(booking -> booking.getStatus().equals(BookingStatus.WAITING)).map(mapper::mapBookingToResponseDto).toList();
    }

    private List<BookingResponseDto> getRejected(long userId){
        return bookingRepository.findByBookerId(userId).stream().filter(booking -> booking.getStatus().equals(BookingStatus.REJECTED)).map(mapper::mapBookingToResponseDto).toList();
    }

    private List<BookingResponseDto> getAll(long userId){
        return bookingRepository.findByBookerId(userId).stream().map(mapper::mapBookingToResponseDto).toList();
    }


}
