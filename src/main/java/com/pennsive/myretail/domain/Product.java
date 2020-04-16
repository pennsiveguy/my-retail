package com.pennsive.myretail.domain;

public class Product {
	private Long id;
	private String name;
	
	public Product(Long productId, String name) {
		id = productId;
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
