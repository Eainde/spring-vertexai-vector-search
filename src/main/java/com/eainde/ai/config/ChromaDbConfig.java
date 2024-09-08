package com.eainde.ai.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vertexai.embedding.VertexAiEmbeddigConnectionDetails;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModel;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModelName;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChromaDbConfig {
    private static final String PROJECT_ID = "eainde";
    private static final String REGION = "europe-west2";
    public static final String ENDPOINT = "europe-west2-aiplatform.googleapis.com:443";
    private static final String PUBLISHER = "goggle";

    @Bean
    public EmbeddingModel embeddingModel() {
        return new VertexAiTextEmbeddingModel(
                new VertexAiEmbeddigConnectionDetails(ENDPOINT, PROJECT_ID, REGION, PUBLISHER),
                obj());
    }

    private VertexAiTextEmbeddingOptions obj(){
        return VertexAiTextEmbeddingOptions.builder()
                .withTaskType(VertexAiTextEmbeddingOptions.TaskType.RETRIEVAL_DOCUMENT)
                .withModel(VertexAiTextEmbeddingModelName.TEXT_EMBEDDING_004)
                .build();
    }

}
