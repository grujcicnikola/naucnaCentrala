package com.example.scientificCenter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Recenzent;
import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.domain.User;
import com.example.scientificCenter.repository.AuthorRepository;
import com.example.scientificCenter.repository.RecenzentRepository;
import com.example.scientificCenter.repository.ScientificAreaRepository;
import com.example.scientificCenter.repository.UserRepository;


@Service
public class UserService {
	
	@Autowired
    private UserRepository repository;
	
	@Autowired
    private AuthorRepository authorRepository;
	
	@Autowired
    private RecenzentRepository recRepository;
	
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	public User save(User user) {
		return repository.save(user);
	}

	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return repository.findByUsername(username);
	}

	public void delete(User user) {
		// TODO Auto-generated method stub
		repository.delete(user);
	}

	public Optional<User> getByEmail(String email) {
		// TODO Auto-generated method stub
		return repository.findByEmail(email);
	}

	public Optional<User> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}
	
	public Recenzent findRecenzentById(Long id) {
		// TODO Auto-generated method stub
		return this.recRepository.findById(id).get();
	}
	
	public List<Recenzent> findAllRecenzents() {
		// TODO Auto-generated method stub
		return this.recRepository.findAll();
	}

}
