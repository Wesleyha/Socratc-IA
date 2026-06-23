package com.example.voiceai.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversation_messages")
public class ConversationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sessionId;

    @Column(length = 5000)
    private String pergunta;

    @Column(length = 10000)
    private String resposta;

    private LocalDateTime dataHora;

    public ConversationMessage() {
    }

    public ConversationMessage(Long sessionId,
                               String pergunta,
                               String resposta) {

        this.sessionId = sessionId;
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}