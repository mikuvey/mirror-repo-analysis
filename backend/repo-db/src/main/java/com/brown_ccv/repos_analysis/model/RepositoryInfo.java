package com.brown_ccv.repos_analysis.model;

import java.util.Date;
import java.util.List;

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
    private int stargazers_count;
    private int forks_count;
    private int open_issues_count;
    private List<String> topics;
    private Date created_at;
    private Date pushed_at;
    private String html_url;
    
    //Lombok will take care of getters and setters and toString
}
