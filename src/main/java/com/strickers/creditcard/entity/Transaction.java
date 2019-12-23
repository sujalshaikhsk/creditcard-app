package com.strickers.creditcard.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "transaction")
@Setter
@Getter
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer transactionId;
	
	private LocalDateTime transactionDate;
	
	@OneToOne
	@JoinColumn(name = "credit_card_number")
	private CreditCard creditCard;
	
	private Double transactionAmount;
	private String remarks;
}
