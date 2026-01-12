package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.constants.Constants;
import ru.practicum.shareit.request.dto.RequestItemDto;
import ru.practicum.shareit.request.dto.RequestItemResponseDto;
import ru.practicum.shareit.request.service.RequestService;

import java.util.List;


@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {
    @Autowired
    private RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    RequestItemResponseDto makeRequest(@RequestHeader(Constants.USER_ID_HEADER) long userId,
                                       @RequestBody RequestItemDto req) {
        return requestService.makeRequest(userId, req);
    }

    //list of request made by user
    @GetMapping
    List<RequestItemResponseDto> getUserRequest(@RequestHeader(Constants.USER_ID_HEADER) long userId) {
        return requestService.getUserRequest(userId);
    }

    @GetMapping("/all")
    List<RequestItemResponseDto> getAllRequest() {
        return requestService.getAllRequest();
    }

    @GetMapping("/{requestId}")
    RequestItemResponseDto getRequest(@PathVariable long requestId) {
        return requestService.getRequest(requestId);
    }

}
