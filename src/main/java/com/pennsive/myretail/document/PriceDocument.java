package com.pennsive.myretail.document;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "item-price")
public class PriceDocument {
	@Id
    private ObjectId id;
	
	@Indexed(unique = true, name="productId")
	private Integer productId;
	
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
