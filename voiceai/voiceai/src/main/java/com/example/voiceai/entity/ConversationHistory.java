package com.example.voiceai.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversation_history")
public class ConversationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String pergunta;

    @Column(length = 10000)
    private String resposta;

    private LocalDateTime dataHora;

    public ConversationHistory() {
    }

    public ConversationHistory(String pergunta, String resposta) {
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}