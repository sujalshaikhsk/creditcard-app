package com.strickers.creditcard.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strickers.creditcard.dto.AccountSummaryResponse;
import com.strickers.creditcard.entity.CreditCard;
import com.strickers.creditcard.entity.Transaction;
import com.strickers.creditcard.exception.TransactionException;
import com.strickers.creditcard.repository.CreditCardRepository;
import com.strickers.creditcard.repository.TransactionRepository;
import com.strickers.creditcard.utils.ApiConstant;
import com.strickers.creditcard.utils.Month;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	CreditCardRepository creditCardRepository;
	
	/*
	 * this method takes two parameters which are customerId and month and finds
	 * the specific month number And with the help of creditCardNumber and month
	 * transactions will be fetched
	 */
	@Override
	public List<AccountSummaryResponse> fetchTransactionsByMonth(Long creditCardNumber, String month)
			throws ParseException, TransactionException {
		log.info("The method fetchTransactionsByMonth() is called in service");
		CreditCard creditCard=creditCardRepository.findByCreditCardNumber(creditCardNumber);
		List<Transaction> transactions=transactionRepository.findByCreditCard(creditCard);
		
		List<AccountSummaryResponse> accountSummaryResponseList = new ArrayList<>();
			for (Transaction transaction : transactions) {

				Integer transactionMonth = transaction.getTransactionDate().getMonthValue();

				int actualMonthNumber = Month.monthStringToInt(month);

				if (actualMonthNumber == transactionMonth) {
					AccountSummaryResponse accountSummaryResponse = new AccountSummaryResponse();
					BeanUtils.copyProperties(transaction, accountSummaryResponse);
					accountSummaryResponseList.add(accountSummaryResponse);
				}
			}
			if(accountSummaryResponseList.isEmpty()) {
				throw new TransactionException(ApiConstant.NO_TRANSACTIONS_FOUND);
			}
		return accountSummaryResponseList;
		
}

}

