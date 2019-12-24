package com.strickers.creditcard.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strickers.creditcard.dto.AccountSummaryResponse;
import com.strickers.creditcard.entity.CreditCard;
import com.strickers.creditcard.entity.Customer;
import com.strickers.creditcard.entity.Transaction;
import com.strickers.creditcard.exception.CustomerNotFoundException;
import com.strickers.creditcard.exception.TransactionException;
import com.strickers.creditcard.repository.CreditCardRepository;
import com.strickers.creditcard.repository.CustomerRepository;
import com.strickers.creditcard.repository.TransactionRepository;
import com.strickers.creditcard.utils.ApiConstant;
import com.strickers.creditcard.utils.Month;

import lombok.extern.slf4j.Slf4j;

/**
 * The TransactionServiceImpl class fetches the list of transaction for the
 * particular year and month
 * 
 * @author Bindushree
 * 
 */
@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CreditCardRepository creditCardRepository;

	/**
	 * this method takes three parameters which are customerId and month and year
	 * and finds the specific month number And with the help of creditCardNumber and
	 * month transactions will be fetched
	 * 
	 * @param customerId
	 * @param month
	 * @param year
	 * @return
	 * @throws ParseException
	 * @throws TransactionException
	 * @throws CustomerNotFoundException
	 */
	@Override
	public List<AccountSummaryResponse> fetchTransactionsByMonth(Long customerId, String month, Integer year)
			throws ParseException, TransactionException, CustomerNotFoundException {
		log.info("The method fetchTransactionsByMonth() is called in service");
		Optional<Customer> customer = customerRepository.findById(customerId);
		if (!customer.isPresent()) {
			throw new CustomerNotFoundException(ApiConstant.CUSTOMER_NOT_FOUND);
		}
		CreditCard creditCard = creditCardRepository.findByCustomer(customer.get());
		List<Transaction> transactions = transactionRepository.findByCreditCard(creditCard);

		List<AccountSummaryResponse> accountSummaryResponseList = new ArrayList<>();
		for (Transaction transaction : transactions) {

			Integer transactionMonth = transaction.getTransactionDate().getMonthValue();
			Integer transactionYear = transaction.getTransactionDate().getYear();
			Integer actualMonthNumber = Month.monthStringToInt(month);

			if (actualMonthNumber.equals(transactionMonth) && transactionYear.equals(year)) {
				AccountSummaryResponse accountSummaryResponse = new AccountSummaryResponse();
				accountSummaryResponse.setCreditCardNumber(creditCard.getCreditCardNumber());
				BeanUtils.copyProperties(transaction, accountSummaryResponse);
				accountSummaryResponseList.add(accountSummaryResponse);
			}
		}
		if (accountSummaryResponseList.isEmpty()) {
			throw new TransactionException(ApiConstant.NO_TRANSACTIONS_FOUND);
		}
		return accountSummaryResponseList;

	}

}
