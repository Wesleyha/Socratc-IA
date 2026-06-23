package com.example.voiceai.repository;

import com.example.voiceai.entity.VoiceMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoiceMessageRepository extends JpaRepository<VoiceMessage, Long> {

}