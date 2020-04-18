package com.pennsive.myretail.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductResponse {
	private Integer id;
	private String name;
	
	@JsonProperty("current_price")
	private PriceResponse price;
	
	public ProductResponse(Integer productId, String productName, PriceResponse price) {
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
