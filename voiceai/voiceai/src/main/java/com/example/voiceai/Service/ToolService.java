package com.example.voiceai.service;

import com.example.voiceai.entity.Reminder;
import com.example.voiceai.repository.ReminderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ToolService {

    private final ReminderRepository reminderRepository;

    public ToolService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public String horaAtual() {
        return "A hora atual é: " + LocalDateTime.now();
    }

    public String salvarLembrete(String texto) {
        Reminder reminder = new Reminder(texto);
        reminderRepository.save(reminder);

        return "Lembrete salvo com sucesso: " + texto;
    }

    public String listarLembretes() {
        List<Reminder> lembretes = reminderRepository.findAll();

        if (lembretes.isEmpty()) {
            return "Você não tem lembretes salvos.";
        }

        StringBuilder resposta = new StringBuilder("Seus lembretes são:\n");

        for (Reminder r : lembretes) {
            resposta.append("- ").append(r.getTexto()).append("\n");
        }

        return resposta.toString();
    }
}