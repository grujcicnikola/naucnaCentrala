package com.example.scientificCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scientificCenter.domain.PDF;


@Repository
public interface PDFRepository extends JpaRepository<PDF, Long>{

	PDF findByName(String id);

	//List<Image> findByAccommodationId(Long id);
}