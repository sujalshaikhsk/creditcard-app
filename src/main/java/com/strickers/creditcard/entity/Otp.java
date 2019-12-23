package com.strickers.creditcard.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "otp")
@Setter
@Getter
public class Otp  {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer otpId;
	
	private Integer otpNumber;
	
	@OneToOne
	@JoinColumn(name = "credit_card_number")
	private CreditCard creditCard;
}
