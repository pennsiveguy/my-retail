package com.pennsive.myretail.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pennsive.myretail.aggregator.ProductAggregatorService;
import com.pennsive.myretail.response.ProductResponse;

@RestController
@RequestMapping(path="/products")
public class ProductController {
	@Autowired
	private ProductAggregatorService productAggregator;
	
	@GetMapping(path="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ProductResponse getProduct(@PathVariable("id") Long id) {
		ProductResponse response = productAggregator.getProduct(id);
		
		return response;
	}
}