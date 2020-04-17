package com.pennsive.myretail.model.external.redsky;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Product {
	private Item item;

	public Item getItem() {
		return item;
	}

	@JsonSetter("item")
	public void setItem(Item item) {
		this.item = item;
	}
}
