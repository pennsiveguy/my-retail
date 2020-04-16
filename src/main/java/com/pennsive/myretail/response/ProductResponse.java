package com.pennsive.myretail.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pennsive.myretail.document.Price;
import com.pennsive.myretail.domain.Product;

public class ProductResponse {
	private Long id;
	private String name;
	
	@JsonProperty("current_price")
	private PriceResponse priceResponse;
	
	public ProductResponse(Product product, Price price) {
		id = product.getId();
		name = product.getName();
		priceResponse = new PriceResponse(price);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
