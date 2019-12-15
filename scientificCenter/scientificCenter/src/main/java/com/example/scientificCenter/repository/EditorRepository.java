package com.example.scientificCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scientificCenter.domain.Author;
import com.example.scientificCenter.domain.Editor;

@Repository
public interface EditorRepository extends JpaRepository<Editor, Long> {

}