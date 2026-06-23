package com.example.voiceai.repository;

import com.example.voiceai.entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository
        extends JpaRepository<ChatSession, Long> {

}