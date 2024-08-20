package com.eainde.ai.service;

import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vertexai.embedding.VertexAiEmbeddigConnectionDetails;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingModel;
import org.springframework.ai.vertexai.embedding.text.VertexAiTextEmbeddingOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VertexEmbeddingService {
    private static final String PROJECT_ID = "cloud-run-project-431416";
    private static final String REGION = "europe-west2";

    public EmbeddingResponse getResponse(final String request) {
        VertexAiEmbeddigConnectionDetails connectionDetails = VertexAiEmbeddigConnectionDetails.builder()
                .withProjectId(PROJECT_ID)
                .withLocation(REGION)
                .build();

        VertexAiTextEmbeddingOptions options = VertexAiTextEmbeddingOptions.builder()
                .withModel(VertexAiTextEmbeddingOptions.DEFAULT_MODEL_NAME)
                .build();

        var embeddingModel = new VertexAiTextEmbeddingModel(connectionDetails, options);

        return embeddingModel.embedForResponse(List.of(request));
    }

    public List<Double> getVectors(final String request) {
        EmbeddingResponse response = getResponse(request);
        float[] vectors = response.getResult().getOutput();
        return convertFloatArrayToList(vectors);
    }

    public static List<Double> convertFloatArrayToList(float[] floatArray) {
        List<Double> doubleList = new ArrayList<>(floatArray.length);
        for (float f : floatArray) {
            doubleList.add((double) f);
        }
        return doubleList;
    }
}
