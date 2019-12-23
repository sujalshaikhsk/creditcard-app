package com.strickers.creditcard.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "credit_card")
@Setter
@Getter
@SequenceGenerator(name = "creditcardsequence", initialValue = 1001001001)
public class CreditCard implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "creditcardsequence")
	private Long creditCardNumber;

	private String customerName;
	private Long mobileNumber;
	private String address;
	private String gender;
	private LocalDate dateOfBirth;
	private Double salary;
	private Double availableBalance;
	private Double creditCardLimit;
	private LocalDate validFrom;
	private LocalDate validTill;
	private String email;
}
