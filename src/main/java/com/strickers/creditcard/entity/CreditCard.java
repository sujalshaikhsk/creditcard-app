package com.strickers.creditcard.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "credit_card")
@Setter
@Getter
@SequenceGenerator(name = "creditcardsequence", initialValue = 1001001001)
public class CreditCard {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "creditcardsequence")
	private Long creditCardNumber;

	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	private Double availableBalance;
	private Double creditCardLimit;
	private LocalDate validFrom;
	private LocalDate validTill;
	private Integer cvv;
	private String status;
	
}
