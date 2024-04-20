package goorm.webide.chat.controller;

import goorm.webide.chat.dto.ChatRoomRequest;
import goorm.webide.chat.dto.ChatRoomResponse;
import goorm.webide.chat.dto.ChatApiResponse;
import goorm.webide.chat.service.ChatRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 검증 실패 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ChatApiResponse<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .findFirst()
                .orElse("잘못된 입력입니다.");

        return ChatApiResponse.fail(errorMessage);
    }

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

    /* 전체 채팅방 목록 조회(GET /chat/rooms) */
    @GetMapping
    public ResponseEntity<ChatApiResponse<List<ChatRoomResponse>>> getAllRooms() {
        List<ChatRoomResponse> roomResponses = chatRoomService.findAllRooms();
        ChatApiResponse<List<ChatRoomResponse>> apiResponse = ChatApiResponse.success(
                roomResponses,
                "전체 채팅방 목록 조회에 성공했습니다."
        );
        return ResponseEntity.ok(apiResponse);
    }


    /* 회원별 채팅방 목록 조회(GET /chat/rooms/user) */
    @GetMapping("/user")
    public ResponseEntity<ChatApiResponse<List<ChatRoomResponse>>> getAllRoomsByUserNo(@RequestParam("userNo") Long userNo) {
        List<ChatRoomResponse> roomResponses = chatRoomService.findAllRoomsByUserId(userNo);
        ChatApiResponse<List<ChatRoomResponse>> apiResponse = ChatApiResponse.success(
                roomResponses,
                "채팅방 목록 조회에 성공했습니다."
        );
        return ResponseEntity.ok(apiResponse);
    }
}