package com.strickers.creditcard.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionRequestDto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer productId;
	private Long creditCardNumber;	
	private Integer otp;
	
	private Double amount;	
	
	private String shippingAddress;
}
