package com.strickers.creditcard.utils;

/**
 * 
 * @author Sujal
 *
 */
public class ApiConstant {
	private ApiConstant() {
	}

	public static final String LOGIN_ERROR = "please enter valid username and password";
	public static final String LOGIN_SUCCESS = "you are successfully logged in";
	public static final String CREDIT_CARD_TYPE = "credit";

	public static final String SUCCESS = "SUCCESSFUL";
	public static final String FAILED = "FAILED";

	public static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
	public static final String VALIDATION_FAILED = "VALIDATION FAILED";
	public static final String NO_ELEMENT_FOUND = "NO ELEMENT FOUND";
	public static final String CUSTOMER_NOT_FOUND = "Not a valid customer";
	public static final String POLICY_NOT_FOUND = "No policy found";
	public static final String POLICY_ALREADY_EXISTS = "Policy already exists for the customer";

	public static final Integer SUCCESS_CODE = 200;
	public static final Integer FAILURE_CODE = 404;
	public static final Integer NO_CONTENT_CODE = 204;
	public static final String POLICY_FAILURE = "The customer is unable to buy policy";
	public static final String POLICY_SUCCESS = "The policy is successfully availed";
	public static final String NOT_PERMITTED = "Your age is not permitted to avail the offer";
	public static final String NO_TRANSACTIONS_FOUND = "There are no transactions for this credit card";
	public static final String CREDITCARD_SUCCESS = "Credit card has been generated";

}
