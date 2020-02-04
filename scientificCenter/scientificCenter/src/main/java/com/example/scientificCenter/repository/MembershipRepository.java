package com.example.scientificCenter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scientificCenter.domain.Editor;
import com.example.scientificCenter.domain.Journal;
import com.example.scientificCenter.domain.Membership;


@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

	Membership findByJournalIdAndAuthorId(Long journalId, Long authorId);

}