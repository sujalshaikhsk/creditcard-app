package com.strickers.creditcard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.strickers.creditcard.entity.CreditCard;
import com.strickers.creditcard.entity.Transaction;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findByCreditCard(CreditCard creditCard);

}
