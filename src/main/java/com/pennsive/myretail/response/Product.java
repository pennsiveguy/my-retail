package com.pennsive.myretail.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.domain.ProductDomain;

public class Product {
	private Long id;
	private String name;
	
	@JsonProperty("current_price")
	private Price price;
	
	public Product(ProductDomain productDomain, PriceDocument priceDocument) {
		id = productDomain.getId();
		name = productDomain.getName();
		price = new Price(priceDocument);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
