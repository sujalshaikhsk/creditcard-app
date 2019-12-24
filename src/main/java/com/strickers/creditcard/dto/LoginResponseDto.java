package com.strickers.creditcard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
	private Integer statusCode;
	private String message;
	private Long customerId;
	private String customerName;
	private String type;
}
