package com.pennsive.myretail.model.external.redsky;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.NoSuchElementException;

public class RedskyResponseV2 {
	@JsonProperty("product")
	private RedskyProduct product;

	public String getTitle() {
		String title = null;
		
		NullPointerException npe = null;
		
		try {
			title = product.getItem().getProductDescription().getTitle();
		} catch(NullPointerException ex) {
			npe = ex;
		}
		
		if(npe != null || title == null || title.isEmpty()) {
			throw new NoSuchElementException("title");
		}
		
		return title;
	}
}
