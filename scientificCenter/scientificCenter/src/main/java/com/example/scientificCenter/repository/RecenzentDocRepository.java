package com.example.scientificCenter.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.scientificCenter.model.RecenzentDoc;

public interface RecenzentDocRepository extends ElasticsearchRepository<RecenzentDoc, Long> {

}
