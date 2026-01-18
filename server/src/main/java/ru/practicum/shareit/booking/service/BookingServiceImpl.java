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
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.WrongRequestException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.DbItemRepository;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserResponseDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final DbItemRepository itemRepository;
    private final UserService userService;
    private final ItemService itemService;
    private final BookingMapper mapper;
    private final UserMapper userMapper;

    @Override
    public BookingResponseDto makeBooking(BookingRequestDto dto, long userId) {
        UserResponseDto user = userService.getUser(userId);
        Item item = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new NotFoundException("Объект не найден"));
//        if (itemService.isUserOwner(item.getId(), userId)) {
//            throw new WrongRequestException("Собственник не может бронировать свои вещи.");
//        }
        if (!item.isAvailable()) {
            throw new WrongRequestException("Объект не доступен для бронирования.");
        }

        Booking booking = mapper.mapRequestDtoToBooking(dto);
        booking.setBooker(userMapper.mapResponseToUser(user));
        booking.setItem(item);
        booking.setStatus(BookingStatus.WAITING);

        return mapper.mapBookingToResponseDto(bookingRepository.save(booking));
    }

    @Override
    public BookingResponseDto approveBooking(long userId, long bookingId, boolean approved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Бронирование не найдено!"));
        if (!itemService.isUserOwner(booking.getItem().getId(), userId)) {
            throw new WrongRequestException("Пользователь не является собственником и не может подтверждать бронь. User id = " + userId);
        }

        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return mapper.mapBookingToResponseDto(bookingRepository.save(booking));
    }

    @Override
    public BookingResponseDto getBookingById(long userId, long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Бронирование не найдено!"));
        log.info("Owner is {}, booker is {}", booking.getItem().getOwnerId(), booking.getBooker());
        if (booking.getBooker().getId() != userId && booking.getItem().getOwnerId() != userId) {
            throw new WrongRequestException("У пользователя нет прав доступа к информации о бронировании.");
        }
        return mapper.mapBookingToResponseDto(booking);
    }

    @Override
    public List<BookingResponseDto> getAllUserBooking(long userId, BookingState state) {
        UserResponseDto user = userService.getUser(userId);
        List<Booking> bookings = bookingRepository.findByBookerId(userId);

        return getListByState(bookings, state);
    }

    @Override
    public List<BookingResponseDto> getAllUserItemBooking(long userId, BookingState state) {
        UserResponseDto user = userService.getUser(userId);
        List<Booking> bookings = bookingRepository.findAllByOwnerId(userId);

        return getListByState(bookings, state);
    }

    private List<BookingResponseDto> getListByState(List<Booking> bookings, BookingState state) {
        return switch (state) {
            case PAST -> getPast(bookings);
            case FUTURE -> getFuture(bookings);
            case CURRENT -> getCurrent(bookings);
            case WAITING -> getWaiting(bookings);
            case REJECTED -> getRejected(bookings);
            default -> getAll(bookings);
        };
    }

    private List<BookingResponseDto> getPast(List<Booking> bookings) {
        LocalDateTime cur = LocalDateTime.now();
        return bookings.stream().filter(booking -> booking.getStart().isBefore(cur) && booking.getEnd().isBefore(cur) && booking.getStatus().equals(BookingStatus.APPROVED)).map(mapper::mapBookingToResponseDto).toList();

    }

    private List<BookingResponseDto> getFuture(List<Booking> bookings) {
        LocalDateTime cur = LocalDateTime.now();
        return bookings.stream().filter(booking -> booking.getEnd().isAfter(cur) && booking.getStatus().equals(BookingStatus.APPROVED)).map(mapper::mapBookingToResponseDto).toList();
    }

    private List<BookingResponseDto> getCurrent(List<Booking> bookings) {
        LocalDateTime cur = LocalDateTime.now();
        return bookings.stream().filter(booking -> booking.getStart().isBefore(cur) && booking.getEnd().isAfter(cur) && booking.getStatus().equals(BookingStatus.APPROVED)).map(mapper::mapBookingToResponseDto).toList();
    }

    private List<BookingResponseDto> getWaiting(List<Booking> bookings) {
        return bookings.stream().filter(booking -> booking.getStatus().equals(BookingStatus.WAITING)).map(mapper::mapBookingToResponseDto).toList();
    }

    private List<BookingResponseDto> getRejected(List<Booking> bookings) {
        return bookings.stream().filter(booking -> booking.getStatus().equals(BookingStatus.REJECTED)).map(mapper::mapBookingToResponseDto).toList();
    }

    private List<BookingResponseDto> getAll(List<Booking> bookings) {
        return bookings.stream().map(mapper::mapBookingToResponseDto).toList();
    }


}
