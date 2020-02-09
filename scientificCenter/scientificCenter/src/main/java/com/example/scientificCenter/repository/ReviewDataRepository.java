package com.example.scientificCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scientificCenter.domain.Recenzent;
import com.example.scientificCenter.domain.ReviewData;


@Repository
public interface ReviewDataRepository extends JpaRepository<ReviewData, Long> {

	ReviewData findByPaperId(Long id);



}