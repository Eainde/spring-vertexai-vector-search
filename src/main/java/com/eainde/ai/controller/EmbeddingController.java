package com.eainde.ai.controller;

import com.eainde.ai.service.VertexEmbeddingService;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmbeddingController {
    private final VertexEmbeddingService vertexEmbeddingService;

    public EmbeddingController(VertexEmbeddingService vertexEmbeddingService) {
        this.vertexEmbeddingService = vertexEmbeddingService;
    }

    @GetMapping("/ai/embedding")
    public EmbeddingResponse embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return vertexEmbeddingService.getResponse(message);
    }
}
