package com.example.voiceai.repository;

import com.example.voiceai.entity.ConversationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationHistoryRepository extends JpaRepository<ConversationHistory, Long> {
}