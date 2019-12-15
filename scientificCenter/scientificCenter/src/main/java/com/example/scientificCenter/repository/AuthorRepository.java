package com.example.scientificCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scientificCenter.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
