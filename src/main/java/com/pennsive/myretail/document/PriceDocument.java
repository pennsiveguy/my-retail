package com.pennsive.myretail.document;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "item-price")
public class PriceDocument {
	@Id
	private Long productId;
	
	private BigDecimal value;
	
	@Field("currency_code")
	private String currencyCode;
	
	public BigDecimal getValue() {
		return value;
	}
	
	public void setValue(BigDecimal newValue) {
		this.value = newValue;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
}
