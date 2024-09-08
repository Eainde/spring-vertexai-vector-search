package com.eainde.ai.controller;

import com.eainde.ai.domain.Chat;
import com.eainde.ai.service.ChromaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ChromaController {
    private final ChromaService chromaService;

    @GetMapping("/ai/embedding/{collectionName}/{message}")
    public String embed(@PathVariable("collectionName") String collectionName, @PathVariable("message") String message) {
        return chromaService.chat(message,collectionName);
    }

    @PostMapping("/chroma/save")
    public String addEmbeddings(@RequestBody Chat chat)  {
        return chromaService.save(chat);
    }
}
