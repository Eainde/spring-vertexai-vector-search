package com.eainde.ai.service;

import com.google.cloud.aiplatform.v1.EndpointName;
import com.google.cloud.aiplatform.v1.PredictRequest;
import com.google.cloud.aiplatform.v1.PredictResponse;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This is different way/implementation of getting the vectors
 * We can ignore this as it involves lot of manual configuration
 */
@Service
@Deprecated
public class PredictTextEmbeddingsSample {
    private static final String ENDPOINT = "europe-west2-aiplatform.googleapis.com:443";
    private static final String PROJECT = "cloud-run-project-431416";
    private static final String MODEL = "text-embedding-004";
    private static final String TASK = "RETRIEVAL_DOCUMENT";
    private static final OptionalInt OUTPUT_DIMENSIONALITY = OptionalInt.of(256);
    public List<List<Float>> predictTextEmbeddings(List<String> texts) throws IOException {
        PredictionServiceSettings settings =
                PredictionServiceSettings.newBuilder().setEndpoint(ENDPOINT).build();
        Matcher matcher = Pattern.compile("^(?<Location>\\w+-\\w+)").matcher(ENDPOINT);
        String location = matcher.matches() ? matcher.group("Location") : "europe-west2";
        EndpointName endpointName =
                EndpointName.ofProjectLocationPublisherModelName(PROJECT, location, "google", MODEL);

        // You can use this prediction service client for multiple requests.
        try (PredictionServiceClient client = PredictionServiceClient.create(settings)) {
            PredictRequest.Builder request =
                    PredictRequest.newBuilder().setEndpoint(endpointName.toString());
            if (OUTPUT_DIMENSIONALITY.isPresent()) {
                request.setParameters(
                        Value.newBuilder()
                                .setStructValue(
                                        Struct.newBuilder()
                                                .putFields("outputDimensionality", valueOf(OUTPUT_DIMENSIONALITY.getAsInt()))
                                                .build()));
            }
            for (String text : texts) {
                request.addInstances(
                        Value.newBuilder()
                                .setStructValue(
                                        Struct.newBuilder()
                                                .putFields("content", valueOf(text))
                                                .putFields("task_type", valueOf(TASK))
                                                .build()));
            }
            PredictResponse response = client.predict(request.build());
            List<List<Float>> floats = new ArrayList<>();
            for (Value prediction : response.getPredictionsList()) {
                Value embeddings = prediction.getStructValue().getFieldsOrThrow("embeddings");
                Value values = embeddings.getStructValue().getFieldsOrThrow("values");
                floats.add(
                        values.getListValue().getValuesList().stream()
                                .map(Value::getNumberValue)
                                .map(Double::floatValue)
                                .collect(Collectors.toList()));
            }
            return floats;
        }
    }

    private static Value valueOf(String s) {
        return Value.newBuilder().setStringValue(s).build();
    }

    private static Value valueOf(int n) {
        return Value.newBuilder().setNumberValue(n).build();
    }
}