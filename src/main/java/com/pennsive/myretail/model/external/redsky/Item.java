package com.pennsive.myretail.model.external.redsky;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Item {
	private ProductDescription productDescription;

	public ProductDescription getProductDescription() {
		return productDescription;
	}

	@JsonSetter("product_description")
	public void setProductDescription(ProductDescription productDescription) {
		this.productDescription = productDescription;
	}

}
