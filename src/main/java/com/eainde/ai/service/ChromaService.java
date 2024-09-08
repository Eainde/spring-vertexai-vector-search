package com.eainde.ai.service;

import com.eainde.ai.config.ChromaDb;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;

@Service
@RequiredArgsConstructor
public class ChromaService {
    private final EmbeddingModel embeddingModel;
    private final ChatLanguageModel chatLanguageModel;

    public String save(final com.eainde.ai.domain.Chat chat){
        TextSegment segment1 = TextSegment.from(chat.getMessage());
        Embedding embedding1 = embeddingModel.embed(segment1).content();
        return ChromaDb.embeddingStore(chat.getCollectionName()).add(embedding1, segment1);
    }



    public String chat(String userMessage, String collectionName) {
        ContentRetriever contentRetriever = new EmbeddingStoreContentRetriever(ChromaDb.embeddingStore(collectionName), embeddingModel);
        Chat chat = AiServices.builder(Chat.class).chatLanguageModel(chatLanguageModel).contentRetriever(contentRetriever).build();
        return chat.chat(userMessage);
    }
}
