package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.RequestItemDto;
import ru.practicum.shareit.request.dto.RequestItemResponseDto;

import java.util.List;

public interface RequestService {
    RequestItemResponseDto makeRequest(long userId, RequestItemDto req);

    List<RequestItemResponseDto> getUserRequest(long userId);

    List<RequestItemResponseDto> getAllRequest();

    RequestItemResponseDto getRequest(long requestId);
}
