package com.strickers.creditcard.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionSummaryResponsedto {
	Integer statusCode;
	String message;
    List<AccountSummaryResponse> transactions;
}
