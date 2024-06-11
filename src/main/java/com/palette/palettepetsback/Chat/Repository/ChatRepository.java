package com.palette.palettepetsback.Chat.Repository;

import com.palette.palettepetsback.Chat.Entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatRoom, Long> {

    @Query("select chatRoomId from ChatRoom where (user1 = ?1 and user2 = ?2) or (user1 = ?2 and user2 = ?1)")
    Optional<String> findChatRoomById(Long user1_id, Long user2_id);
}
