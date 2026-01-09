package ru.practicum.shareit.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.request.dto.RequestItemDto;
import ru.practicum.shareit.request.dto.RequestItemResponseDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "created", source = "created")
    ItemRequest mapRequestDtoToItemRequest(RequestItemDto dto, User user, LocalDateTime created);

    RequestItemResponseDto mapItemRequestToResponse(ItemRequest itemRequest);
}
