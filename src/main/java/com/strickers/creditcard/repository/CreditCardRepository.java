package com.strickers.creditcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.strickers.creditcard.entity.CreditCard;


@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

}
