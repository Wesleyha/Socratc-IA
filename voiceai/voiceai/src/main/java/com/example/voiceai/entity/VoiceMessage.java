package com.example.voiceai.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "voice_messages")
public class VoiceMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    public VoiceMessage() {
    }

    public Long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}