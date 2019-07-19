package com.rms.rms.common.exception;

/**
 * homertruong
 */

public class InvalidViewModelException extends RuntimeException {

	private static final long serialVersionUID = -4060640148400587305L;

	// DiscountCode
	public static final String DISCOUNT_CODE_START_DATE_INVALID_MESSAGE = "DiscountCode start_date must >= today!";
	public static final String DISCOUNT_CODE_DATES_INVALID_MESSAGE = "DiscountCode start_date must <= endDate!";

	// Voucher
	public static final String VOUCHER_START_DATE_INVALID_MESSAGE = "Voucher start_date must >= today!";
	public static final String VOUCHER_DATES_INVALID_MESSAGE = "Voucher start_date must < end_date!";

	public InvalidViewModelException() {
		super();
	}

	public InvalidViewModelException(String msg) {
		super(msg);
	}

	public InvalidViewModelException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
