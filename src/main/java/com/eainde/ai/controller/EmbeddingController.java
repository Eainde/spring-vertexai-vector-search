package com.eainde.ai.controller;

//import com.eainde.ai.service.MovieAtlasSearchService;
import com.eainde.ai.service.VertexEmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmbeddingController {
    private final VertexEmbeddingService vertexEmbeddingService;
    private final EmbeddingModel embeddingModel;

    @GetMapping("/ai/embedding")
    public EmbeddingResponse embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return vertexEmbeddingService.getResponse(message);
    }

    @GetMapping("/aii/embedding")
    public Map embedd(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        EmbeddingResponse embeddingResponse = this.embeddingModel.embedForResponse(List.of(message));
        return Map.of("embedding", embeddingResponse);
    }
}
