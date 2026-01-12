package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.constants.Constants;
import ru.practicum.shareit.request.dto.RequestDto;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> makeRequest(@RequestHeader(Constants.USER_ID_HEADER) long userId,
                                              @Valid @RequestBody RequestDto req) {
        log.info("Creating request by user = {}", userId);
        return requestClient.makeRequest(userId, req);
    }

    @GetMapping
    public ResponseEntity<Object> getUserRequest(@RequestHeader(Constants.USER_ID_HEADER) long userId) {
        log.info("Getting all request made by user id = {}", userId);
        return requestClient.getUserRequest(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequest(@RequestHeader(Constants.USER_ID_HEADER) long userId) {
        log.info("Getting all requests");
        return requestClient.getAllRequest(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest(@RequestHeader(Constants.USER_ID_HEADER) long userId,
                                             @PathVariable long requestId) {
        log.info("Getting request by id = {}", requestId);
        return requestClient.getRequest(userId, requestId);
    }
}
