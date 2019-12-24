package com.strickers.creditcard.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreditcardResponseDto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long creditCardNumber;
	
	private Double availableBalance;
	private Double creditCardLimit;
	private LocalDate validFrom;
	private LocalDate validTill;
	private Integer cvv;
	private String status;
	
	private Integer statusCode;
    private String message;

}
