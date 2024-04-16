package goorm.webide.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * packageName    : goorm.webide.chat.entity
 * fileName       : ChatRoom
 * author         : won
 * date           : 2024/04/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024/04/14        won       최초 생성
 */

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomNo;

    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<ChatUser> chatUsers= new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Chat> chats = new ArrayList<>();
}
