package com.pennsive.myretail.model.external.redsky;

import com.fasterxml.jackson.annotation.JsonSetter;

public class RedskyItem {
	private RedskyProductDescription productDescription;

	public RedskyProductDescription getProductDescription() {
		return productDescription;
	}

	@JsonSetter("product_description")
	public void setProductDescription(RedskyProductDescription productDescription) {
		this.productDescription = productDescription;
	}

}
