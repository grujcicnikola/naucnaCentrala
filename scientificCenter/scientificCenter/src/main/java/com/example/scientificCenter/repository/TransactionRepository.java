package com.example.scientificCenter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.scientificCenter.domain.ScientificArea;
import com.example.scientificCenter.domain.Transaction;
import com.example.scientificCenter.domain.TransactionStatus;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findAllByBuyerEmailAndStatus(String userEmail, TransactionStatus ts);

	Transaction findOneByOrderId(String id);

	List<Transaction> findAllByBuyerEmail(String userEmail);

}