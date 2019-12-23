package com.strickers.creditcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.strickers.creditcard.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {

	@Query("select o from Otp o where o.creditCard.creditCardNumber=:creditCardNumber")
	Otp getOtpbyCreditCardNumber(@Param("creditCardNumber") Long creditCardNumber);

}
