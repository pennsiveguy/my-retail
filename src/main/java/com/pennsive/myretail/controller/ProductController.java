package com.pennsive.myretail.controller;

import com.pennsive.myretail.aggregator.ProductAggregator;
import com.pennsive.myretail.response.PriceResponse;
import com.pennsive.myretail.response.ProductResponse;
import com.pennsive.myretail.service.PriceDocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path="/products")
@Tag(description = "the Products API", name = "Products")
public class ProductController {
	@Autowired
	private ProductAggregator productAggregator;

	@Autowired
	private PriceDocumentService priceDocumentService;
	
	@GetMapping(path = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Find a product by its numeric ID. "
		+ "<p>Some IDs which return results are 54191101, 53727884, and 13860428. </p>"
		+ "<p>Non-numeric IDs (i.e. 'foobar') will be flagged as bad requests.</p>"
		+ "<p>Failure to submit an ID will result in a 404 - Not Found"
		+ "</p>",
	responses = {
		@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ProductResponse.class)),
			description = "Request successful"),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema()),
			description = "No product exists with that ID. Try <a href='https://amazon.com'>Amazon</a>. KIDDING!!!"),
		@ApiResponse(responseCode = "400", content = @Content(schema = @Schema()),
			description = "The product ID must be a number")
	})
	public ResponseEntity<ProductResponse> getProduct(
		@Parameter(description = "The numeric ID of the product whose data you wish to retrieve", required = true) @PathVariable("id") Integer id) {
		return productAggregator.getProduct(id);
	}
	
	@PutMapping(path = "/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@Operation(description = "Update an item's price. Pick one of the IDs above.",
	responses = {
		@ApiResponse(responseCode = "200", description = "Update successful"),
		@ApiResponse(responseCode = "404", description = "No product exists with that ID"),
		@ApiResponse(responseCode = "400", description = "The product ID must be a number")
	})
	public void updatePrice(
		@Parameter(description = "The new price", required = true) @Valid @RequestBody PriceResponse price,
		@Parameter(description = "The numeric ID of the product whose price you wish to update", required = true) @PathVariable("id") Integer id) {
		priceDocumentService.updatePrice(id, price);
	}
}