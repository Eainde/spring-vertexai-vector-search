package com.eainde.ai.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.chroma.ChromaEmbeddingStore;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ChromaDb {
    public static final String VECTOR_DB_HOST = "http://localhost:8001/";
    public static final String API_CHROMA_COLLECTIONS_URL = VECTOR_DB_HOST+"/api/v1/collections";

    public static EmbeddingStore<TextSegment> embeddingStore(final String collectionName){
        return ChromaEmbeddingStore.builder()
                .baseUrl(VECTOR_DB_HOST)
                .collectionName(collectionName)
                .timeout(Duration.ofSeconds(30))
                .logRequests(true)
                .logResponses(true)
                .build();
    }


}
