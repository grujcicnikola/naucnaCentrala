package com.example.scientificCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scientificCenter.domain.Coauthor;
import com.example.scientificCenter.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
