package com.strickers.creditcard.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.strickers.creditcard.dto.CreditcardRequestDto;
import com.strickers.creditcard.entity.CreditCard;
import com.strickers.creditcard.entity.Customer;
import com.strickers.creditcard.repository.CreditCardRepository;
import com.strickers.creditcard.repository.CustomerRepository;
import com.strickers.creditcard.utils.CreditcardValidator;
import com.strickers.creditcard.utils.StringConstant;
import com.strickers.creditcard.utils.Utils;

/**
 * This service is used to create and manipulate credit card
 * @author Sujal
 *
 */
@Service
public class CreditcardServiceImpl implements CreditcardService {

	@Qualifier(value = "creditcardValidator")
	@Autowired
	private CreditcardValidator<CreditcardRequestDto> creditcardValidator;

	@Autowired
	private CreditCardRepository creditCardRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(CreditcardServiceImpl.class);

	/**
	 * 
	 * @param CreditcardRequestDto
	 * @return Optional<CreditCard>
	 */
	@Override
	public Optional<CreditCard> createCreditCard(CreditcardRequestDto creditcardRequestDto) {
		Customer customer1 = null;
		CreditCard creditCard=null;
		if (creditcardValidator.validate(creditcardRequestDto)) {
			creditcardRequestDto.setPassword(Utils.encrypt(creditcardRequestDto.getPassword()));
			Customer customer = saveCustomer(creditcardRequestDto);
			if (!Objects.isNull(customer)) {
				logger.info("customer id "+customer.getCustomerId());
				BeanUtils.copyProperties(creditcardRequestDto, customer);
				customer.setStatus(StringConstant.ACTIVE_STATUS);
				customer.setCreatedDate(LocalDate.now());
				customer1 = customerRepository.save(customer);
			}
			if (!Objects.isNull(customer1)) {
				creditCard= generateCreditcard(customer1);
				return Optional.of(creditCard);
			}

		}
		return Optional.ofNullable(null);
	}

	/**
	 * 
	 * @param customer1
	 * @return CreditCard
	 */
	private CreditCard generateCreditcard(Customer customer1) {
		CreditCard creditCard = new CreditCard();
		Double balance= customer1.getSalary()*3;
		creditCard.setCreditCardLimit(balance);
		creditCard.setAvailableBalance(balance);
		creditCard.setValidFrom(LocalDate.now());
		creditCard.setValidTill(LocalDate.now().plusYears(StringConstant.VALID_TILL));
		creditCard.setStatus(StringConstant.ACTIVE_STATUS);
		creditCard.setCustomer(customer1);
		creditCard.setCvv(Utils.generateCVV());
		return creditCardRepository.save(creditCard);	
	}

	/**
	 * 
	 * @param creditcardRequestDto
	 * @return Customer
	 */
	public Customer saveCustomer(CreditcardRequestDto creditcardRequestDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<CreditcardRequestDto> entity = new HttpEntity<>(creditcardRequestDto, headers);

		String url = "http://localhost:8084/shoppingcart/customers";

		return restTemplate.exchange(url, HttpMethod.POST, entity, Customer.class).getBody();
	}

}
