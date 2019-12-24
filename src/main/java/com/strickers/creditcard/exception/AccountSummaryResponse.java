package com.strickers.creditcard.exception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSummaryResponse {
	
	private double transactionAmount;
	private LocalDateTime transactionDate;
	private String remarks;
	private Long creditCardNumber;

}



