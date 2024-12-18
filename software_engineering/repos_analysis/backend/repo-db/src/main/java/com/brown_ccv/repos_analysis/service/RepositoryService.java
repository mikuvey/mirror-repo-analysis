package com.brown_ccv.repos_analysis.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.brown_ccv.repos_analysis.model.RepositoryInfo;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class RepositoryService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${search.index}")
    private String index;

    public List<RepositoryInfo> filterAndSearchRepositories(String search, Boolean archived, String sortBy, String order, int page, int pageSize) {
        List<AggregationOperation> pipeline = new ArrayList<>();
    
        //Search with autocomplete (refer mongoDB aggregation pipeline for more info)
        if (search != null && !search.isEmpty()) {

            pipeline.add(context -> new Document("$search", 
            new Document("index", index)
                    .append("autocomplete", 
            new Document("query", search)
                        .append("path", "name")
                        .append("fuzzy", 
            new Document("maxEdits", 1L)))));
        }
    
        //Filter by archived status if provided
        if (archived != null) {
            pipeline.add(Aggregation.match(Criteria.where("archived").is(archived)));
        }
    
        //Sort the results
        pipeline.add(Aggregation.sort(getSortOrder(sortBy, order)));
    
        //Add pagination stages
        pipeline.add(Aggregation.skip((long) page * pageSize));
        pipeline.add(Aggregation.limit(pageSize));
    
        //Execute the aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(pipeline);
        List<RepositoryInfo> ret = mongoTemplate.aggregate(aggregation, "repositories", RepositoryInfo.class).getMappedResults();
        log.info("{} repos fetched from DB", ret.size());
        return ret;
    }
    
    private Sort getSortOrder(String sortBy, String order) {
        //Default sorting field
        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "stars"; // Default to stars
        }
    
        //OrderBy
        Sort.Direction direction = "asc".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC;
    
        //Sorting parameters
        switch (sortBy) {
            case "stars":
                return Sort.by(direction, "stargazers_count");
            case "forks":
                return Sort.by(direction, "forks_count");
            case "issues":
                return Sort.by(direction, "open_issues_count");
            case "date":
                return Sort.by(direction, "pushed_at");
            default:
                throw new IllegalArgumentException("Invalid sortBy value: " + sortBy);
        }
    }
    
    
}
