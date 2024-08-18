package com.eainde.ai.service;

import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vertexai.embedding.VertexAiEmbeddigConnectionDetails;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModel;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingOptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VertexEmbeddingService {
    private static final String PROJECT_ID = "cloud-run-project-431416";
    private static final String REGION = "europe-west2";

    public EmbeddingResponse getResponse(final String request) {
        VertexAiEmbeddigConnectionDetails connectionDetails =
                VertexAiEmbeddigConnectionDetails.builder()
                        .withProjectId(PROJECT_ID)
                        .withLocation(REGION)
                        .build();

        VertexAiTextEmbeddingOptions options = VertexAiTextEmbeddingOptions.builder()
                .withModel(VertexAiTextEmbeddingOptions.DEFAULT_MODEL_NAME)
                .build();

        var embeddingModel = new VertexAiTextEmbeddingModel(connectionDetails, options);

        return embeddingModel.embedForResponse(List.of(request));
    }
}
