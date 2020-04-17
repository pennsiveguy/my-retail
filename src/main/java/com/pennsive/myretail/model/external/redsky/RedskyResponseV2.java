package com.pennsive.myretail.model.external.redsky;

import java.util.NoSuchElementException;

import com.fasterxml.jackson.annotation.JsonSetter;

public class RedskyResponseV2 {
	private Product product;

	@JsonSetter("product")
	public void setProduct(Product product) {
		this.product = product;
	}

	public String getTitle() {
		String title = null;
		
		try {
			title = product.getItem().getProductDescription().getTitle();
		} catch(NullPointerException npe) {
			throw new NoSuchElementException("title");
		}
		
		return title;
	}
}
