package com.strickers.creditcard.service;

import java.util.Optional;
import com.strickers.creditcard.entity.CreditCard;

import com.strickers.creditcard.dto.CreditcardRequestDto;

public interface CreditcardService {

	Optional<CreditCard> createCreditCard(CreditcardRequestDto creditcardRequestDto);

}
