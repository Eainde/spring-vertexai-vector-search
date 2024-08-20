package com.eainde.ai.service;

import com.eainde.ai.document.Movies;
import com.eainde.ai.repository.MoviesRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.search.FieldSearchPath;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.search.SearchPath.fieldPath;
import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class MovieAtlasSearchService {
    @Value("${spring.data.mongodb.database}")
    private String appDatabase;

    private final MongoClient mongoClient;
    private final VertexEmbeddingService vertexEmbeddingService;
    private final MoviesRepository moviesRepository;

    public Collection<Document> findByVectorData(String message) {
        List<Double> queryVector = vertexEmbeddingService.getVectors(message);
        MongoDatabase database = mongoClient.getDatabase(appDatabase);
        MongoCollection<Document> collection = database.getCollection("movies");
        String indexName = "moviesPlotIndexx";
        FieldSearchPath fieldSearchPath = fieldPath("plot_embedding");
        int numCandidates = 10;
        int limit = 5;

        List<Bson> pipeline = asList(
                vectorSearch(fieldSearchPath, queryVector, indexName, numCandidates, limit),
                project(fields(excludeId(), include("title", "year", "plot", "fullplot", "imdb.rating"))));

        return collection.aggregate(pipeline).into(new ArrayList<>());
    }

    public void saveEmbeddings() {
        List<Movies> movies = moviesRepository.findAll();
        movies.stream().filter(movie -> movie.getPlot() != null).forEach(movie -> {
            List<Double> vectorEmbeddings = vertexEmbeddingService.getVectors(movie.getPlot());
            addEmbeddings(movie, vectorEmbeddings);
        });
    }

    public void addEmbeddings(Movies movie, List<Double> vectorEmbeddings) {
        movie.setVectorData(vectorEmbeddings);
        moviesRepository.save(movie);
    }
}
