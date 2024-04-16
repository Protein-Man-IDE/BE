package goorm.webide.chat.controller;

import goorm.webide.chat.dto.ChatRoomRequest;
import goorm.webide.chat.dto.ChatRoomResponse;
import goorm.webide.chat.entity.ChatApiResponse;
import goorm.webide.chat.service.ChatRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : goorm.webide.chat.controller
 * fileName       : ChatRoomController
 * author         : won
 * date           : 2024/04/15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/15        won       최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/rooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    /* 채팅방 생성(POST /chat/rooms) */
    @PostMapping
    public ResponseEntity<ChatApiResponse<ChatRoomResponse>> createChatRoom(
            @Valid @RequestBody ChatRoomRequest roomRequest
    ) {
        ChatRoomResponse roomResponse = chatRoomService.createChatRoom(roomRequest);
        ChatApiResponse<ChatRoomResponse> apiResponse = ChatApiResponse.success(
                roomResponse,
                "채팅방이 생성되었습니다."
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}