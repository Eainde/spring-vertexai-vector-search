package com.eainde.ai.controller;

import com.eainde.ai.service.MovieAtlasSearchService;
import com.eainde.ai.service.PredictTextEmbeddingsSample;
import com.eainde.ai.service.VertexEmbeddingService;
import com.mongodb.client.AggregateIterable;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
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
    private final MovieAtlasSearchService movieAtlasSearchService;
    private final PredictTextEmbeddingsSample predictTextEmbeddingsSample;

    @GetMapping("/ai/embedding")
    public EmbeddingResponse embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return vertexEmbeddingService.getResponse(message);
    }

    @GetMapping("/ai/java/embedding")
    public List<List<Float>>  javaEmbed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) throws IOException {
        return predictTextEmbeddingsSample.predictTextEmbeddings(List.of(message));
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
