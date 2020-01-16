package com.example.scientificCenter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scientificCenter.domain.Author;
import com.example.scientificCenter.domain.Editor;
import com.example.scientificCenter.domain.Journal;

@Repository
public interface EditorRepository extends JpaRepository<Editor, Long> {

	Optional<Editor> findEditorByJournal(Journal journal);

}