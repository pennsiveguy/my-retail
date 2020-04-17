package com.pennsive.myretail.model.external.redsky;

import com.fasterxml.jackson.annotation.JsonSetter;

public class ProductDescription {
	private String title;

	public String getTitle() {
		return title;
	}
	
	@JsonSetter("title")
	public void setTitle(String title) {
		this.title = title;
	}
}
