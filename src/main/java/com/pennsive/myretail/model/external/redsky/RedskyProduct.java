package com.pennsive.myretail.model.external.redsky;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedskyProduct {
	@JsonProperty("item")
	private RedskyItem item;

	public RedskyItem getItem() {
		return item;
	}
}
