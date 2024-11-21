package com.brown_ccv.repos_analysis.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.brown_ccv.repos_analysis.model.RepositoryInfo;

@Service
public class RepositoryService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<RepositoryInfo> filterAndSearchRepositories(String search, Boolean archived, String sortBy) {
        List<AggregationOperation> pipeline = new ArrayList<>();

        // If no search is provided, include all repositories
        if (search != null && !search.isEmpty()) {
            pipeline.add(context -> new Document("$search", 
                new Document("index", "search-repo-names")
                .append("text", 
                new Document("query", search)
                .append("path", "name")
                )
            ));
        }
    
        // Filter by archived status if provided
        if (archived != null) {
            pipeline.add(Aggregation.match(Criteria.where("archived").is(archived)));
        }
    
        // Always include a sort stage (default or user-provided)
        pipeline.add(Aggregation.sort(getSortOrder(sortBy)));

        // Add pagination stages
        // pipeline.add(Aggregation.skip((long) page * pageSize)); // Skip documents based on page
        // pipeline.add(Aggregation.limit(pageSize)); // Limit the number of results per page

    
        // Execute the aggregation pipeline
        Aggregation aggregation = Aggregation.newAggregation(pipeline);
        return mongoTemplate.aggregate(aggregation, "repositories", RepositoryInfo.class).getMappedResults();
    }
        
    private Sort getSortOrder(String sortBy) {
        if (sortBy == null || sortBy.isEmpty()) {
            // Default sorting: stars descending
            return Sort.by(Sort.Order.desc("stargazers_count"));
        }
    
        // Map sorting options to fields
        switch (sortBy) {
            case "stars_low_to_high":
                return Sort.by(Sort.Order.asc("stargazers_count"));
            case "stars_high_to_low":
                return Sort.by(Sort.Order.desc("stargazers_count"));
            case "forks_low_to_high":
                return Sort.by(Sort.Order.asc("forks_count"));
            case "forks_high_to_low":
                return Sort.by(Sort.Order.desc("forks_count"));
            case "issues_low_to_high":
                return Sort.by(Sort.Order.asc("open_issues_count"));
            case "issues_high_to_low":
                return Sort.by(Sort.Order.desc("open_issues_count"));
            case "date_asc":
                return Sort.by(Sort.Order.asc("pushed_at"));
            case "date_desc":
                return Sort.by(Sort.Order.desc("pushed_at"));
            default:
                throw new IllegalArgumentException("Invalid sortBy value: " + sortBy);
        }
    }
    
}
