package com.strickers.creditcard.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Setter
@Getter
public class Customer implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long customerId;

	private String customerName;
	private Long mobileNumber;
	private String address;
	private String gender;
	private LocalDate dateOfBirth;
	private Double salary;
	private String email;
	private String password;
	private String status;
	private LocalDate createdDate;
}
