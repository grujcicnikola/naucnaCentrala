package com.example.scientificCenter.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.scientificCenter.model.PaperDoc;
import com.example.scientificCenter.model.RecenzentDoc;
import com.example.scientificCenter.repository.PaperDocRepository;
import com.example.scientificCenter.repository.RecenzentDocRepository;
import com.example.scientificCenter.serviceInterface.PaperDocDAO;
import com.example.scientificCenter.serviceInterface.RecenzentDocDAO;
import org.springframework.stereotype.Service;

@Service
public class RecenzentDocService  implements RecenzentDocDAO {
	@Autowired
    private RecenzentDocRepository recenzentRepository;
	
	public RecenzentDocService() {
	}
	
	public Iterable<RecenzentDoc> findAll() {
		// TODO Auto-generated method stub
		return recenzentRepository.findAll();
	}
	
	
	
	public boolean add(RecenzentDoc unit){
		unit = recenzentRepository.index(unit);
		if(unit!=null)
			return true;
		else
			return false;
	}

	public boolean update(RecenzentDoc unit){
		unit = recenzentRepository.save(unit);
		if(unit!=null)
			return true;
		else
			return false;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	


	

}
