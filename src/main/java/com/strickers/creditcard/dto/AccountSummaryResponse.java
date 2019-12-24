package com.strickers.creditcard.dto;

import java.time.LocalDateTime;

import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountSummaryResponse {
	
	private double transactionAmount;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime transactionDate;
	private String remarks;
	private Long creditCardNumber;

}



