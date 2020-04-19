package com.pennsive.myretail.model.external.redsky;

import java.util.NoSuchElementException;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedskyResponseV2 {
	@JsonProperty("product")
	private RedskyProduct product;

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
