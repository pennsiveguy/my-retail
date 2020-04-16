package com.pennsive.myretail.controller;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pennsive.myretail.aggregator.ProductAggregator;
import com.pennsive.myretail.response.Price;
import com.pennsive.myretail.response.Product;
import com.pennsive.myretail.service.PriceDocumentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path="/products")
public class ProductController {
	@Autowired
	private ProductAggregator productAggregator;

	@Autowired
	private PriceDocumentService priceDocumentService;
	
	@GetMapping(path = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Find a product",
	responses = {
			@ApiResponse(content = @Content(schema = @Schema(implementation = Product.class)), responseCode = "200"),
			@ApiResponse(content = @Content(schema = @Schema(implementation = Null.class)), responseCode = "404", description = "No product exists with that ID."),
			@ApiResponse(content = @Content(schema = @Schema(implementation = Null.class)), responseCode = "400", description = "The product ID must be a number.")
	})
	public Product getProduct(@Parameter(description = "The product ID you wish to look up", required = true) @PathVariable("id") Long id) {
		return productAggregator.getProduct(id);
	}
	
	@PutMapping(path = "/{id}")
	@Operation(description = "Update an item's price",
	responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", description = "No product exists with that ID."),
			@ApiResponse(responseCode = "400", description = "The product ID must be a number.")
	})
	public void updatePrice(
			@Parameter(description = "The new price", required = true) @RequestBody Price price,
			@Parameter(description = "The product ID whose price you wish to update", required = true) @PathVariable("id") Long id) {
		priceDocumentService.updatePrice(id, price);
	}
}