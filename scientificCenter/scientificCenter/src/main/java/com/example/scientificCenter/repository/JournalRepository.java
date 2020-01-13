package com.example.scientificCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scientificCenter.domain.Author;
import com.example.scientificCenter.domain.Journal;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {

	Journal findByIssn(String string);

}