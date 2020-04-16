package com.pennsive.myretail.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.pennsive.myretail.document.Price;

public class PriceResponse {
	private BigDecimal value;
	private String currencyCode;
	
	public PriceResponse(final Price price) {
		value = price.getValue();
		currencyCode = price.getCurrencyCode();
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	@JsonGetter("currency_code")
	public String getCurrencyCode() {
		return currencyCode;
	}
}
