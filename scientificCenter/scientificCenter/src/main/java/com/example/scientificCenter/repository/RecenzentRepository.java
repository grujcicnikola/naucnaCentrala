package com.example.scientificCenter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scientificCenter.domain.Author;
import com.example.scientificCenter.domain.Recenzent;
import com.example.scientificCenter.domain.User;


@Repository
public interface RecenzentRepository extends JpaRepository<Recenzent, Long> {



}