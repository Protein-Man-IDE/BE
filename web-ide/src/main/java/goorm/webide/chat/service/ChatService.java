package goorm.webide.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import goorm.webide.chat.dto.ChatRequest;
import goorm.webide.chat.dto.ChatResponse;
import goorm.webide.chat.entity.Chat;
import goorm.webide.chat.entity.ChatRoom;
import goorm.webide.chat.repository.ChatRepository;
import goorm.webide.chat.repository.ChatRoomRepository;
import goorm.webide.user.entity.User;
import goorm.webide.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * packageName    : goorm.webide.chat.service
 * fileName       : ChatService
 * author         : won
 * date           : 2024/04/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/14        won       최초 생성
 */

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public ChatResponse saveChat(ChatRequest chatRequest, Long roomNo) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(chatRequest);

        ChatRoom chatRoom = chatRoomRepository.findById(roomNo)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));
        User user = userRepository.findById(chatRequest.getUserNo())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Chat chat = Chat.builder()
                .chatTxt(json)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .chatRoom(chatRoom)
                .user(user)
                .build();
        chatRepository.save(chat);

        return new ChatResponse(
                user.getUserNo(),
                chatRoom.getRoomNo(),
                chat.getChatNo(),
                chat.getChatTxt(),
                chat.getCreatedAt()
        );
    }
}
