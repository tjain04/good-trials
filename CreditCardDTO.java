package com.jwt.rest;

import java.util.Date;

public class CreditCardDTO {
	private String cardNumber;
	private Date expiryDate;
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	@Override
	public String toString() {
		return "CreditCardDTO [cardNumber=" + cardNumber + ", expiryDate=" + expiryDate + "]";
	}
	
}
