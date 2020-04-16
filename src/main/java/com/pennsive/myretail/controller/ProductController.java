package com.pennsive.myretail.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pennsive.myretail.aggregator.ProductAggregatorService;
import com.pennsive.myretail.response.Price;
import com.pennsive.myretail.response.Product;
import com.pennsive.myretail.service.PriceDocumentService;

@RestController
@RequestMapping(path="/products")
public class ProductController {
	@Autowired
	private ProductAggregatorService productAggregator;

	@Autowired
	private PriceDocumentService priceDocumentService;
	
	@GetMapping(path = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Product getProduct(@PathVariable("id") Long id) {
		return productAggregator.getProduct(id);
	}
	
	@PostMapping(path = "/{id}")
	public void updatePrice(@RequestBody Price price, @PathVariable("id") Long id) {
		priceDocumentService.updatePrice(id, price);
	}
}