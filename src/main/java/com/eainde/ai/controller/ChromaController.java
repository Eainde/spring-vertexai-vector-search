package com.eainde.ai.controller;

import com.eainde.ai.service.ChromaService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChromaController {
    private final ChromaService chromaService;

    @GetMapping("/chroma/embedding")
    public List<Document> embed() {
        return chromaService.retrieve();
    }

    @PostMapping("/chroma/save")
    public void addEmbeddings()  {
        chromaService.save();
    }
}
