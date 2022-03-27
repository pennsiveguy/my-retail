package com.pennsive.myretail.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ProductResponse {
	private Integer id;
	private String name;
	
	@JsonProperty("current_price")
	private PriceResponse price;
	
	@JsonCreator
	public ProductResponse(
			@JsonProperty("id") Integer productId, 
			@JsonProperty("name") String productName,
			@JsonProperty("current_price") PriceResponse price) {
		id = productId;
		name = productName;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public PriceResponse getPrice() {
		return price;
	}
}
