package com.example.scientificCenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Coauthor;
import com.example.scientificCenter.domain.User;
import com.example.scientificCenter.repository.AuthorRepository;
import com.example.scientificCenter.repository.CoauthorsRepository;
import com.example.scientificCenter.repository.UserRepository;


@Service
public class CoauthorService {
		
		@Autowired
	    private CoauthorsRepository repository;
		
		
		
		public Coauthor save(Coauthor user) {
			return repository.save(user);
		}
		
		public Coauthor findById(Long user) {
			return repository.findById(user).get();
		}

		public List<Coauthor> findAllByPaper(Long paperId) {
			// TODO Auto-generated method stub
			return repository.findByPapers_Id(paperId);
		}
}
