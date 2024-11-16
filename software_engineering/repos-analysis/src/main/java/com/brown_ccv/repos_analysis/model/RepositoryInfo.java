package com.brown_ccv.repos_analysis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryInfo {
    Long id;
    String name;
}
