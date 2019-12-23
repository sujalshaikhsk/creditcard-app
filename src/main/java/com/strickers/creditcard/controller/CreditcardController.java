package com.strickers.creditcard.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.strickers.creditcard.dto.CreditcardRequestDto;
import com.strickers.creditcard.dto.CreditcardResponseDto;
import com.strickers.creditcard.entity.CreditCard;
import com.strickers.creditcard.service.CreditcardService;
import com.strickers.creditcard.utils.ApiConstant;
import com.strickers.creditcard.utils.StringConstant;

/**
 * @author Sujal
 *
 */
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/creditcards")
public class CreditcardController {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(CreditcardController.class);
	/**
	 * The transactionServiceImpl.
	 */
	@Autowired
	private CreditcardService creditcardService;

	/**
	 * 
	 * @description this method is used to do fund transfer in the application
	 * @param fundTransferRequestDto the fundTransferRequestDto which contains
	 *                               fromAccount,toAccount,amount,transactionType
	 *                               and benefactorName
	 * @return fundTransferResponseDto
	 * @throws CommonException
	 */
	@PostMapping()
	public ResponseEntity<CreditcardResponseDto> createCreditCard(
			@RequestBody CreditcardRequestDto creditcardRequestDto) {
		logger.debug("In createCreditCard");
		CreditcardResponseDto creditcardResponseDto =  new CreditcardResponseDto();
		Optional<CreditCard> OptionalCeditCard = creditcardService.createCreditCard(creditcardRequestDto);
		
		if(OptionalCeditCard.isPresent()) {
			BeanUtils.copyProperties(OptionalCeditCard.get(), creditcardResponseDto);
			creditcardResponseDto.setMessage(ApiConstant.SUCCESS);
			creditcardResponseDto.setStatusCode(ApiConstant.SUCCESS_CODE);
		}else {
			creditcardResponseDto.setMessage(ApiConstant.FAILED);
			creditcardResponseDto.setStatusCode(ApiConstant.FAILURE_CODE);
		}
		return new ResponseEntity<>(creditcardResponseDto, HttpStatus.OK);
	}

}
