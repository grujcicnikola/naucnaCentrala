package com.example.scientificCenter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.scientificCenter.domain.Transaction;
import com.example.scientificCenter.domain.TransactionStatus;
import com.example.scientificCenter.domain.User;
import com.example.scientificCenter.repository.TransactionRepository;
import com.example.scientificCenter.repository.UserRepository;



@Service
public class TransactionService {
	
	@Autowired
    private TransactionRepository repository;
	
	public List<Transaction> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	public Transaction save(Transaction user) {
		return repository.save(user);
	}

	public List<Transaction> findAllByBuyerEmailAndStatus(String userEmail, TransactionStatus ts) {
		// TODO Auto-generated method stub
		return repository.findAllByBuyerEmailAndStatus(userEmail, ts);
	}

	public Transaction findOneByOrderId(String id) {
		// TODO Auto-generated method stub
		return repository.findOneByOrderId(id);
	}

	public List<Transaction> findAllByBuyerEmail(String userEmail) {
		// TODO Auto-generated method stub
		return repository.findAllByBuyerEmail(userEmail);
	}
	
	public TransactionStatus findStatus(String compare) {
		if (compare.equals("created")) {
			return TransactionStatus.CREATED;
		}else if (compare.equals("paid")) {
			return TransactionStatus.PAID;
		}else if (compare.equals("error")) {
			return TransactionStatus.ERROR;
		}else if (compare.equals("unfinished")) {
			return TransactionStatus.UNFINISHED;
		}else if (compare.equals("failed")) {
			return TransactionStatus.FAILED;
		}
		return null;
	}
	
}