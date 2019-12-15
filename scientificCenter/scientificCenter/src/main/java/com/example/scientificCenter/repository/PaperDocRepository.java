package com.example.scientificCenter.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.scientificCenter.model.PaperDoc;

public interface PaperDocRepository extends ElasticsearchRepository<PaperDoc, Long> {

}


