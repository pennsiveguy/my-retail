package com.pennsive.myretail.document;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "item-price")
public class Price {
	private BigDecimal value;
	
	@Field("currency_code")
	private String currencyCode;
	
	public BigDecimal getValue() {
		return value;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
}
