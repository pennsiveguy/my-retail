package com.pennsive.myretail.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pennsive.myretail.domain.Product;

@Service
public class ProductService {
	protected static final String UNKNOWN_ITEM_TITLE = "Unknown Item";
	
	@Autowired
	private RestTemplate redskyRestTemplate;
	
	@Value("${redsky.full.url}")
	private String redskyFullUrl;
	
	public Product getProduct(Long productId) {
		
		Map<String, Long> params = new HashMap<>();
		params.put("id", productId);
	
	    String result = redskyRestTemplate.getForObject(redskyFullUrl, String.class, params);
	    ObjectMapper mapper = new ObjectMapper();
	    String title;
		try {
			JsonNode titleNode = mapper.readTree(result).get("product").get("item").get("product_description").get("title");
			title = titleNode.asText();
		} catch (JsonProcessingException e) {
			title = UNKNOWN_ITEM_TITLE;
		}
		
		return new Product(productId, title);
	}
}
