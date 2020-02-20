package com.example.scientificCenter.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.model.PaperDocRejected;

public interface PaperDocRejectedRepository extends ElasticsearchRepository<PaperDocRejected, Long> {

}

