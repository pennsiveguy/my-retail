package com.pennsive.myretail.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.pennsive.myretail.document.PriceDocument;

public class Price {
	private BigDecimal value;
	private String currencyCode;
	
	public Price() {
		
	}
	
	public Price(final PriceDocument priceDocument) {
		value = priceDocument.getValue();
		currencyCode = priceDocument.getCurrencyCode();
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	@JsonGetter("currency_code")
	public String getCurrencyCode() {
		return currencyCode;
	}
}
