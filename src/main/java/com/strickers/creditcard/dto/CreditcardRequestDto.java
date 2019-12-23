package com.strickers.creditcard.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreditcardRequestDto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String customerName;
	private Long mobileNumber;
	private String address;
	private String gender;
	private LocalDate dateOfBirth;
	private Double salary;
	private String email;
	private String password;

}
