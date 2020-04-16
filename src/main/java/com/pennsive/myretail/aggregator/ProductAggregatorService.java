package com.pennsive.myretail.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennsive.myretail.document.Price;
import com.pennsive.myretail.domain.Product;
import com.pennsive.myretail.response.ProductResponse;
import com.pennsive.myretail.service.PriceService;
import com.pennsive.myretail.service.ProductService;

@Service
public class ProductAggregatorService {
	@Autowired
	private ProductService productService;

	@Autowired
	private PriceService priceService;
	
	public ProductResponse getProduct(Long productId){
		
		Product product = null;
		product = productService.getProduct(productId);
		
		Price price = priceService.getPrice(productId);
		
		return aggregateProduct(product, price);
	}
	
	protected ProductResponse aggregateProduct(Product product, Price price) {
		return new ProductResponse(product, price);
	}
}
