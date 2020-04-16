package com.pennsive.myretail.aggregator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennsive.myretail.document.Price;
import com.pennsive.myretail.service.PriceService;
import com.pennsive.myretail.service.ProductService;

@Service
public class ProductAggregatorService {
	@Autowired
	private ProductService productService;

	@Autowired
	private PriceService priceService;
	
	public Map<String, Object> getProduct(Long productId){
		Map<String, Object> product = new HashMap<>();
		
		Price price = priceService.getPrice(productId);
		
		product.put("current_price", price);
		
		return product;
	}
}
