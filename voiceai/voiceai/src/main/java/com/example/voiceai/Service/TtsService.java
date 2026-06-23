package com.example.voiceai.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
public class TtsService {

    public String gerarAudio(String texto) {

        try {
            new File("audios").mkdirs();

            String nomeArquivo = UUID.randomUUID() + ".mp3";
            String caminho = "audios/" + nomeArquivo;

            ProcessBuilder pb = new ProcessBuilder(
                    "python3",
                    "-m",
                    "edge_tts",
                    "--voice",
                    "pt-BR-AntonioNeural",
                    "--text",
                    texto,
                    "--write-media",
                    caminho
            );

            pb.redirectErrorStream(true);

            Process processo = pb.start();
            int codigo = processo.waitFor();

            if (codigo != 0) {
                throw new RuntimeException("edge-tts falhou. Código: " + codigo);
            }

            return nomeArquivo;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar áudio: " + e.getMessage(), e);
        }
    }
}