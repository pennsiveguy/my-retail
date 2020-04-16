package com.pennsive.myretail.service;

import org.springframework.stereotype.Service;

import com.pennsive.myretail.domain.Product;

@Service
public class ProductService {	
	public Product getProduct(Long productId) {
		Product product = new Product();
		product.setId(productId);
		
		return product;
	}
}
