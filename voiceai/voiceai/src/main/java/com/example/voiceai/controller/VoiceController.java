package com.example.voiceai.controller;

import com.example.voiceai.entity.VoiceMessage;
import com.example.voiceai.repository.VoiceMessageRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/mensagens")
public class VoiceController {

    private final VoiceMessageRepository repository;

    public VoiceController(VoiceMessageRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public VoiceMessage salvar(@RequestBody VoiceMessage mensagem) {
        return repository.save(mensagem);
    }

    @GetMapping
    public List<VoiceMessage> listar() {
        return repository.findAll();
    }
}