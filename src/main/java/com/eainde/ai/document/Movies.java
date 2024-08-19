package com.eainde.ai.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class Movies {
    @Id
    private String id;
    @Field("plot")
    private String plot;
    @Field("title")
    private String title;
    @Field("fullplot")
    private String fullPlot;
    @Field("plot_embedding")
    private List<Double> vectorData;



}
