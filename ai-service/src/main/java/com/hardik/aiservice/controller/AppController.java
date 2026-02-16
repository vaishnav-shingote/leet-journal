package com.hardik.aiservice.controller;

import com.hardik.aiservice.service.AiService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin(origins = "${app.cors.allowed-origins:http://localhost:5173}")
public class AppController {

    private final AiService aiService;

    public AppController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/chat")
    public Flux<String> hello(@RequestParam String prompt) {
        return aiService.response(prompt);
    }
}
