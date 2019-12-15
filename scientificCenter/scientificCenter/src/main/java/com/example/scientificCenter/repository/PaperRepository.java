package com.example.scientificCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.scientificCenter.domain.Paper;
import com.example.scientificCenter.model.PaperDoc;


@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {

	

}