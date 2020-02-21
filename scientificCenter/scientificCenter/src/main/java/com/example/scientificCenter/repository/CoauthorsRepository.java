package com.example.scientificCenter.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.scientificCenter.domain.Author;
import com.example.scientificCenter.domain.Coauthor;


@Repository
public interface CoauthorsRepository extends JpaRepository<Coauthor, Long> {

	List<Coauthor> findByPapers_Id(Long paperId);
}
