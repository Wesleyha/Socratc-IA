package com.example.voiceai.repository;

import com.example.voiceai.entity.ConversationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationMessageRepository
        extends JpaRepository<ConversationMessage, Long> {

    List<ConversationMessage>
    findBySessionIdOrderByDataHoraAsc(Long sessionId);
}