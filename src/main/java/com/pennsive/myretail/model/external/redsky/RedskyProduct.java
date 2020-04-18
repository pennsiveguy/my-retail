package com.pennsive.myretail.model.external.redsky;

import com.fasterxml.jackson.annotation.JsonSetter;

public class RedskyProduct {
	private RedskyItem item;

	public RedskyItem getItem() {
		return item;
	}

	@JsonSetter("item")
	public void setItem(RedskyItem item) {
		this.item = item;
	}
}
