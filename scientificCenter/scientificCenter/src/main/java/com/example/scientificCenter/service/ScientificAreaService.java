package com.example.scientificCenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.repository.ScientificAreaRepository;


@Service
public class ScientificAreaService {
	
	@Autowired
    private ScientificAreaRepository repository;
	
	public List<ScientificArea> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	public Optional<ScientificArea> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

}
