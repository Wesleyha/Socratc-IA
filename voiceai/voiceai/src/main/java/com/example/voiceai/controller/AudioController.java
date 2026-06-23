package com.example.voiceai.controller;

import com.example.voiceai.service.TtsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/audio")
public class AudioController {

    private final TtsService ttsService;

    public AudioController(TtsService ttsService) {
        this.ttsService = ttsService;
    }

    @GetMapping
    public String teste() {
        return "AudioController funcionando!";
    }

    @PostMapping
    public Map<String, String> gerarAudio(@RequestBody String texto) {
        String arquivo = ttsService.gerarAudio(texto);

        return Map.of(
                "url",
                "http://localhost:8080/audios/" + arquivo
        );
    }
}