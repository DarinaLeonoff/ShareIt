package ru.practicum.shareit.request.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.item.AnswerDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.dto.RequestItemDto;
import ru.practicum.shareit.request.dto.RequestItemResponseDto;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestMapper mapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @Override
    public RequestItemResponseDto makeRequest(long userId, RequestItemDto req) {
        User user = userMapper.mapResponseToUser(userService.getUser(userId));
        ItemRequest itemRequest = mapper.mapRequestDtoToItemRequest(req, user, LocalDateTime.now());
        log.info("Mapped request id = {}", itemRequest.getId());
        ItemRequest saved = requestRepository.save(itemRequest);
        log.info("Saved request id = {}", saved.getId());
        return mapper.mapItemRequestToResponse(saved);
    }

    @Override
    public List<RequestItemResponseDto> getUserRequest(long userId) {
        userService.getUser(userId);
        return setAnswers(requestRepository
                .findAllByUser_IdOrderByCreatedDesc(userId).stream()
                .map(mapper::mapItemRequestToResponse).toList());
    }

    @Override
    public List<RequestItemResponseDto> getAllRequest() {
        return setAnswers(requestRepository.findAllByOrderByCreatedDesc().stream()
                .map(mapper::mapItemRequestToResponse).toList());
    }

    @Override
    public RequestItemResponseDto getRequest(long requestId) {
        return setAnswers(List.of(mapper.mapItemRequestToResponse(requestRepository.findById(requestId))))
                .getFirst();
    }

    private List<RequestItemResponseDto> setAnswers(List<RequestItemResponseDto> requests) {
        Map<Long, List<AnswerDto>> answers = itemService
                .getItemAnswerForRequests(requests.stream().map(RequestItemResponseDto::getId).toList());

        for (RequestItemResponseDto request : requests) {
            request.setItems(answers.get(request.getId()));
        }
        return requests;
    }

}
