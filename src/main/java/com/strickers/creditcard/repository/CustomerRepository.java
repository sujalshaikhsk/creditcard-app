package com.strickers.creditcard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.strickers.creditcard.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByMobileNumber(Long mobileNumber);

	Optional<Customer> findByMobileNumberAndPassword(Long mobileNumber, String password);
}
