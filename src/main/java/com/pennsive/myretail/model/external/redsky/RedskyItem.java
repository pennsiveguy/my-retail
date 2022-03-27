package com.pennsive.myretail.model.external.redsky;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedskyItem {
	@JsonProperty("product_description")
	private RedskyProductDescription productDescription;

	public RedskyProductDescription getProductDescription() {
		return productDescription;
	}
}
