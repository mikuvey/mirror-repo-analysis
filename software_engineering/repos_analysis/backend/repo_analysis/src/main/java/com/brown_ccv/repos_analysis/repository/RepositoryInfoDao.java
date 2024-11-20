package com.brown_ccv.repos_analysis.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.brown_ccv.repos_analysis.model.RepositoryInfo;

public interface RepositoryInfoDao extends MongoRepository<RepositoryInfo, Long> {
    RepositoryInfo findByName(String name); //Custom query to find by name
}
