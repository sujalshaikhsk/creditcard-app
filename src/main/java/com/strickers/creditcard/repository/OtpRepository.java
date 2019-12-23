package com.strickers.creditcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.strickers.creditcard.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {

}
