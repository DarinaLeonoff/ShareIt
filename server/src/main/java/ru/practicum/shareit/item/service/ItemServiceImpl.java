package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDateDto;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.exceptions.WrongRequestException;
import ru.practicum.shareit.item.dto.comment.CommentResponseDto;
import ru.practicum.shareit.item.dto.comment.NewCommentDto;
import ru.practicum.shareit.item.dto.item.AnswerDto;
import ru.practicum.shareit.item.dto.item.ItemRequestDto;
import ru.practicum.shareit.item.dto.item.ItemResponseDto;
import ru.practicum.shareit.item.dto.item.ItemWithCommentAndBookingDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.DbItemRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {

    private final DbItemRepository itemRepository;
    private final UserService userService;
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public ItemServiceImpl(DbItemRepository itemRepository, UserService userService,
                           ItemMapper itemMapper, UserMapper userMapper, CommentMapper commentMapper,
                           CommentRepository commentRepository, BookingRepository bookingRepository,
                           BookingMapper bookingMapper) {
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.itemMapper = itemMapper;
        this.userMapper = userMapper;
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    @Transactional
    public ItemResponseDto createItem(long userId, ItemRequestDto itemDto) {
        userService.getUser(userId);
        Item item = itemMapper.mapToItem(itemDto);
        item.setOwnerId(userId);
        return itemMapper.mapItemToResponse(itemRepository.save(item));
    }

    @Override
    public ItemResponseDto getItemDtoById(long itemId) {
        return itemMapper.mapItemToResponse(itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Предмет с таким id(" + itemId + ") не найден")));
    }

    @Override
    @Transactional
    public ItemResponseDto editItem(long userId, ItemRequestDto itemDto, long itemId) {
        Item oldItem = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Предмет с таким id(" + itemId + ") не найден"));
        if (oldItem.getOwnerId() != userId) {
            log.info("Попытка редактировать карточку предмета другого пользователя");
            throw new NotFoundException("Пользователь не является собственником данной вещи");
        }
        Item newItem = itemMapper.updateItem(oldItem, itemDto);
        return itemMapper.mapItemToResponse(itemRepository.save(newItem));
    }

    @Override
    public List<ItemResponseDto> search(String text) {
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }
        return itemMapper.mapItemsToResponses(itemRepository.searchByText(text));
    }

    @Override
    public CommentResponseDto addComment(long userId, long itemId, NewCommentDto dto) {
        checkUserHasBooking(userId, itemId);
        Comment com = commentMapper.mapNewCommentToComment(dto, userMapper.mapResponseToUser(userService.getUser(userId)), itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Пользователь не найден.")), LocalDateTime.now());
        return commentMapper.mapCommentToResponse(commentRepository.save(com));
    }

    @Override
    public List<ItemWithCommentAndBookingDto> getAllUserItems(long userId) {
        List<Booking> bookings = bookingRepository.findByBookerId(userId);
        log.info("All bookings: {}", bookings);
        List<Comment> comments = commentRepository.findAllByItemOwnerId(userId);
        List<ItemWithCommentAndBookingDto> items = itemRepository.findByOwnerId(userId).stream()
                .map(i -> {
                    List<BookingDateDto> lastNextBooking = getLastNextBooking(bookings);
                    log.info("For item {}: last booking ={}, next = {}", i.getId(), lastNextBooking.get(0), lastNextBooking.get(1));
                    List<CommentResponseDto> itemComments = comments.stream()
                            .filter(c -> c.getItem().getId() == i.getId())
                            .map(commentMapper::mapCommentToResponse)
                            .toList();
                    return itemMapper.mapItemToItemWithBooking(i, lastNextBooking.get(0), lastNextBooking.get(1), itemComments);
                }).toList();
        return items;
    }

    @Override
    public ItemWithCommentAndBookingDto getItemWithCommentById(long userId, long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Предмет не найден"));

        List<Booking> bookings = bookingRepository.findByItemId(itemId);
        List<Comment> comments = commentRepository.findByItemId(itemId);

        List<BookingDateDto> lastNextBooking;
        if (userId != item.getOwnerId()) {
            lastNextBooking = Arrays.asList(null, null);
        } else {
            lastNextBooking = getLastNextBooking(bookings);
        }
        List<CommentResponseDto> itemComments = comments.stream()
                .filter(c -> c.getItem().getId() == itemId)
                .map(commentMapper::mapCommentToResponse)
                .toList();

        return itemMapper.mapItemToItemWithBooking(item, lastNextBooking.get(0), lastNextBooking.get(1), itemComments);
    }

    @Override
    public boolean isUserOwner(long itemId, long userId) {
        return itemRepository.findById(itemId).orElseThrow().getOwnerId() == userId;
    }

    @Override
    public Map<Long, List<AnswerDto>> getItemAnswerForRequests(List<Long> requestIds) {
        List<Item> answers = itemRepository.findAllByRequestIdIn(requestIds);
        Map<Long, List<AnswerDto>> result = new HashMap<>();
        for (Long id : requestIds) {
            result.put(id, answers.stream().filter(i -> i.getRequestId() == id)
                    .map(itemMapper::mapItemToAnswer).toList());
        }
        return result;
    }

    private void checkUserHasBooking(long userId, long itemId) {
        List<Booking> bookings = bookingRepository.findByBookerId(userId)
                .stream().filter(b -> b.getItem().getId() == itemId
                        && b.getStatus() == BookingStatus.APPROVED
                        && b.getEnd().isBefore(LocalDateTime.now())).toList();

        if (bookings.isEmpty()) {
            throw new WrongRequestException("Пользователь который еще не пользовался предметом, не может оставить на"
                    + " него отзыв.");
        }
    }

    private List<BookingDateDto> getLastNextBooking(List<Booking> bookings) {
        Booking last = bookings.stream()
                .filter(b -> b.getEnd().isBefore(LocalDateTime.now()))
                .max(Comparator.comparing(Booking::getEnd))
                .orElse(null);
        Booking next = bookings.stream()
                .filter(b -> b.getStart().isAfter(LocalDateTime.now()))
                .min(Comparator.comparing(Booking::getStart))
                .orElse(null);


        return Arrays.asList(last != null ? bookingMapper.mapBookingToDateDto(last) : null,
                next != null ? bookingMapper.mapBookingToDateDto(next) : null);
    }

}
