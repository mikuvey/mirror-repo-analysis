package com.brown_ccv.repos_analysis.model;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryInfo {
    @Id
    Long id;
    String name;
    boolean archived;
}
