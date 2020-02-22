package com.example.scientificCenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Paper;
import com.example.scientificCenter.repository.PaperRepository;

@Service
public class PaperService {
	
	@Autowired
    private PaperRepository paperRepository;

	public Paper save(Paper paper) {
		return this.paperRepository.save(paper);
		
	}

	public Paper findByTitle(String title) {
		// TODO Auto-generated method stub
		return this.paperRepository.findByTitle(title);
	}

	public Optional<Paper> findById(Long id) {
		// TODO Auto-generated method stub
		return this.paperRepository.findById(id);
	}

	public List<Paper> findAll() {
		// TODO Auto-generated method stub
		return this.paperRepository.findAll();
	}
}
