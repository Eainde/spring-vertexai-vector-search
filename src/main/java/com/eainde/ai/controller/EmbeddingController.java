package com.eainde.ai.controller;

import com.eainde.ai.service.MovieAtlasSearchService;
import com.eainde.ai.service.VertexEmbeddingService;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class EmbeddingController {
    private final VertexEmbeddingService vertexEmbeddingService;
    private final MovieAtlasSearchService movieAtlasSearchService;

    @GetMapping("/ai/embedding")
    public EmbeddingResponse embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return vertexEmbeddingService.getResponse(message);
    }

    @GetMapping("/movies/with")
    Collection<Document> getMoviesWithKeywords(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return movieAtlasSearchService.findByVectorData(message);
    }

    @PostMapping("/ai/save/embedding")
    public void  addEmbeddings() throws IOException {
        movieAtlasSearchService.saveEmbeddings();
    }
}
