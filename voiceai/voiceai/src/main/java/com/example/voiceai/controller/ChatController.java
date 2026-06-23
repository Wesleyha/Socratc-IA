package com.example.voiceai.controller;

import com.example.voiceai.entity.ChatSession;
import com.example.voiceai.entity.ConversationMessage;
import com.example.voiceai.repository.ChatSessionRepository;
import com.example.voiceai.repository.ConversationMessageRepository;
import com.example.voiceai.service.ToolService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatClient chatClient;
    private final ToolService toolService;
    private final ChatSessionRepository sessionRepository;
    private final ConversationMessageRepository messageRepository;

    public ChatController(ChatClient.Builder builder,
                          ToolService toolService,
                          ChatSessionRepository sessionRepository,
                          ConversationMessageRepository messageRepository) {
        this.chatClient = builder.build();
        this.toolService = toolService;
        this.sessionRepository = sessionRepository;
        this.messageRepository = messageRepository;
    }

    @PostMapping
    public ConversationMessage chat(@RequestParam(required = false) Long sessionId,
                                    @RequestBody String pergunta) {

        ChatSession session;

        if (sessionId == null) {
            session = sessionRepository.save(
                    new ChatSession(gerarTitulo(pergunta))
            );
        } else {
            session = sessionRepository.findById(sessionId)
                    .orElseGet(() ->
                            sessionRepository.save(
                                    new ChatSession(gerarTitulo(pergunta))
                            )
                    );
        }

        String texto = pergunta.toLowerCase();
        String resposta;

        if (texto.contains("que horas") || texto.contains("hora atual")) {
            resposta = toolService.horaAtual();

        } else if (
                texto.contains("listar lembretes") ||
                        texto.contains("meus lembretes") ||
                        texto.contains("liste meus lembretes")
        ) {
            resposta = toolService.listarLembretes();

        } else if (
                texto.contains("salvar lembrete") ||
                        texto.contains("salve lembrete") ||
                        texto.contains("criar lembrete")
        ) {
            String lembrete = pergunta
                    .replace("salvar lembrete", "")
                    .replace("salve lembrete", "")
                    .replace("criar lembrete", "")
                    .replace("Salvar lembrete", "")
                    .replace("Salve lembrete", "")
                    .replace("Criar lembrete", "")
                    .trim();

            if (lembrete.isEmpty()) {
                resposta = "Diga o texto do lembrete.";
            } else {
                resposta = toolService.salvarLembrete(lembrete);
            }

        } else {
            String contexto = montarContexto(session.getId());

            String promptFinal = """
                     "Você é um Tutor Socrático de redação ENEM.\\n\\n"
                         "Apresentese somente com seu nome\\n"
                    
                         "OBJETIVO:\\n"
                         "Ensinar o aluno a construir seu raciocínio.\\n\\n"
                    
                         "REGRA:\\n"
                         "- Ensine o PROCESSO, nunca entregue o CONTEÚDO.\\n\\n"
                    
                         "PROIBIDO:\\n"
                         "- Escrever redação ou parágrafos\\n"
                         "- Dar argumentos prontos ou exemplos copiáveis\\n\\n"
                    
                         "PERMITIDO:\\n"
                         "- Explicar passo a passo\\n"
                         "- Dar direções gerais (causas, consequências, etc.)\\n"
                         "- Ensinar estrutura e organização\\n\\n"
                    
                         "COMPORTAMENTO ADAPTATIVO:\\n"
                         "- No início: use mais perguntas (método socrático)\\n"
                         "- Após cerca de 5 perguntas na conversa:\\n"
                         "  • Passe a explicar mais\\n"
                         "  • Dê mais direção\\n"
                         "  • Continue sem entregar respostas prontas\\n"
                         "- Mesmo se o aluno disser que não entendeu:\\n"
                         "  • Explique melhor\\n"
                         "  • Use passos simples\\n"
                         "  • NÃO abandone a ajuda\\n\\n"
                    
                         "COMO RESPONDER:\\n"
                         "- Misture explicação + pergunta\\n"
                         "- Nunca deixe só perguntas secas\\n"
                         "- Termine com 1 ou 2 perguntas\\n\\n"
                    
                         "SE PEDIR IDEIA:\\n"
                         "- Ensine COMO gerar ideias, sem dar conteúdo pronto\\n\\n"
                    
                         "SE PEDIR 'FAÇA PARA MIM':\\n"
                         "- Recuse e pergunte a dificuldade\\n\\n"
                    
                         "FOCO:\\n"
                         "- Tese | Argumento | Intervenção\\n\\n"
                    
                         "ESTILO:\\n"
                         "- Claro, direto e paciente\\n"
                    """.formatted(contexto, pergunta);

            resposta = chatClient.prompt()
                    .user(promptFinal)
                    .call()
                    .content();
        }

        return messageRepository.save(
                new ConversationMessage(
                        session.getId(),
                        pergunta,
                        resposta
                )
        );
    }

    @GetMapping("/sessoes")
    public List<ChatSession> listarSessoes() {
        return sessionRepository.findAll();
    }

    @GetMapping("/sessoes/{id}/mensagens")
    public List<ConversationMessage> mensagensDaSessao(@PathVariable Long id) {
        return messageRepository.findBySessionIdOrderByDataHoraAsc(id);
    }

    private String montarContexto(Long sessionId) {
        List<ConversationMessage> mensagens =
                messageRepository.findBySessionIdOrderByDataHoraAsc(sessionId);

        if (mensagens.isEmpty()) {
            return "Sem histórico anterior.";
        }

        StringBuilder contexto = new StringBuilder();

        int inicio = Math.max(0, mensagens.size() - 6);

        for (int i = inicio; i < mensagens.size(); i++) {
            ConversationMessage msg = mensagens.get(i);

            contexto.append("Usuário perguntou: ")
                    .append(msg.getPergunta())
                    .append("\n");

            contexto.append("VoiceAI respondeu: ")
                    .append(msg.getResposta())
                    .append("\n\n");
        }

        return contexto.toString();
    }

    private String gerarTitulo(String pergunta) {
        String texto = pergunta.trim();

        if (texto.isEmpty()) {
            return "Nova conversa";
        }

        String[] palavras = texto.split("\\s+");
        StringBuilder titulo = new StringBuilder();

        for (int i = 0; i < Math.min(5, palavras.length); i++) {
            titulo.append(palavras[i]).append(" ");
        }

        return titulo.toString().trim();
    }
}