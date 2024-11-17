package com.brown_ccv.repos_analysis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "repositories")
public class RepositoryInfo {
    @Id
    private Long id; 
    private String name;
    private boolean archived;
    
    //Lombok will take care of getters and setters
}
