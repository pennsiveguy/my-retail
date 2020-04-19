package com.pennsive.myretail.model.external.redsky;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedskyProductDescription {
	@JsonProperty("title")
	private String title;

	public String getTitle() {
		return title;
	}
}
