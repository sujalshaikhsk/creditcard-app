package com.strickers.creditcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.strickers.creditcard.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {

	/**
	 * This method is used to get the last generated OTP by the credit card number
	 * 
	 * @param creditCardNumber
	 * @return Otp
	 */
	@Query(nativeQuery = true, value = "select * from otp o where o.credit_card_number=:creditCardNumber order by o.otp_id desc limit 1")
	Otp getOtpbyCreditCardNumber(@Param("creditCardNumber") Long creditCardNumber);

}
