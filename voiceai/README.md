# VoiceAI

VoiceAI é uma API com interface web feita em Java com Spring Boot.  
O projeto permite conversar com uma IA local usando voz, texto, banco de dados e Tool Calling.

## Funcionalidades

- API REST com Spring Boot
- Integração com IA local usando Ollama
- Modelo TinyLlama
- Reconhecimento de voz pelo navegador
- Resposta por voz
- Tool Calling
- Salvamento de conversas no PostgreSQL
- Histórico lateral de conversas
- Sistema de lembretes
- Endpoint para receber áudio
- Interface web em tema escuro

## Tecnologias usadas

- Java 17
- Spring Boot
- Spring AI
- Ollama
- TinyLlama
- PostgreSQL
- Maven
- HTML, CSS e JavaScript

## Estrutura do projeto

```text
src
└── main
    ├── java
    │   └── com.example.voiceai
    │       ├── controller
    │       │   ├── ChatController.java
    │       │   ├── VoiceController.java
    │       │   └── AudioController.java
    │       │
    │       ├── entity
    │       │   ├── VoiceMessage.java
    │       │   ├── Reminder.java
    │       │   └── ConversationHistory.java
    │       │
    │       ├── repository
    │       │   ├── VoiceMessageRepository.java
    │       │   ├── ReminderRepository.java
    │       │   └── ConversationHistoryRepository.java
    │       │
    │       ├── service
    │       │   └── ToolService.java
    │       │
    │       └── VoiceaiApplication.java
    │
    └── resources
        ├── static
        │   └── chat.html
        │
        └── application.properties


Como funciona

O fluxo principal do sistema é:

Usuário fala ou digita
        ↓
A mensagem é enviada para a API
        ↓
O Spring Boot processa a requisição
        ↓
A IA local responde usando Ollama
        ↓
O sistema executa ferramentas, se necessário
        ↓
A conversa é salva no PostgreSQL
        ↓
A resposta aparece na tela
        ↓
O usuário pode ouvir a resposta em voz alta

Requisitos

Antes de executar o projeto, instale:

Java 17
IntelliJ IDEA
PostgreSQL
Ollama
Git
Maven
Configurando o PostgreSQL

Crie um banco de dados chamado:

CREATE DATABASE voiceai;

Depois configure o arquivo:

src/main/resources/application.properties

Exemplo:

spring.datasource.url=jdbc:postgresql://localhost:5432/voiceai
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=tinyllama

server.port=8080

Troque SUA_SENHA pela senha do PostgreSQL.

Configurando o Ollama

Instale o Ollama e depois execute:

ollama pull tinyllama

Para testar:

ollama run tinyllama

Se o modelo responder, está funcionando.

Como executar o projeto

No IntelliJ:

Abra a pasta do projeto.
Espere o Maven carregar.
Execute a classe:
VoiceaiApplication.java

Se tudo estiver certo, aparecerá no terminal:

Started VoiceaiApplication

Acesse no navegador:

http://localhost:63342/voiceai/static/chat.html
Endpoints principais
Chat com IA
POST /chat

Recebe uma mensagem em texto e retorna uma resposta da IA.

Exemplo:

Qual a capital do Brasil?

Resposta:

A capital do Brasil é Brasília.
Histórico de conversas
GET /chat/historico

Retorna todas as conversas salvas no banco.

Mensagens
POST /mensagens

Salva uma mensagem no banco.

GET /mensagens

Lista mensagens salvas.

Áudio
POST /audio

Recebe um arquivo de áudio enviado por formulário.

Tool Calling

O projeto possui ferramentas que executam ações reais no Java.

Exemplos:

Que horas são?

O sistema chama uma função Java que retorna a hora atual.

Salve lembrete estudar Java

O sistema salva um lembrete no PostgreSQL.

Liste meus lembretes

O sistema busca os lembretes salvos no banco.

Exemplos de comandos

Você pode testar no chat:

Olá
Que horas são?
Salve lembrete estudar Java
Liste meus lembretes
Qual a capital do Brasil?
Interface

A interface possui:

Campo de texto
Botão para enviar mensagem
Botão para falar usando microfone
Botão para ouvir a resposta
Histórico de conversas na lateral
Tema escuro em cinza e preto
Banco de dados

O projeto cria automaticamente as tabelas:

voice_messages
reminders
conversation_history

A tabela conversation_history salva:

id
pergunta
resposta
data e hora
Objetivo do projeto

O objetivo do VoiceAI é demonstrar uma aplicação Java moderna com:

API REST
IA local
Reconhecimento de voz
Text-to-Speech
Tool Calling
Banco de dados
Interface web
Autor

Desenvolvido por Wesley.
# Socratc-IA
