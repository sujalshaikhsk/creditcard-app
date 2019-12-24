package com.strickers.creditcard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpRequestDto {

	private Long creditCardNumber;
    private Integer cvv;
}
