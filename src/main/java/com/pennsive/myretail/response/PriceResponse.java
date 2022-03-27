package com.pennsive.myretail.response;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PriceResponse {
	@NotNull
	@Min(0)
	private BigDecimal value;
	private String currencyCode;
	
	public PriceResponse() {}
	
	public PriceResponse(BigDecimal value, String currencyCode) {
		this.value = value;
		this.currencyCode = currencyCode;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	
	@JsonGetter("currency_code")
	public String getCurrencyCode() {
		return currencyCode;
	}
}
