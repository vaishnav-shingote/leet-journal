package com.hardik.aiservice.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AiService {

    private final ChatClient chatClient;

    public AiService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public Flux<String> response(String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content();
    }
}
