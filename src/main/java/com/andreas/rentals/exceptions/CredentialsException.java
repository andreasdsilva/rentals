package com.andreas.rentals.exceptions;

public class CredentialsException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public CredentialsException(String msg) {
		super(msg);
	}
}
