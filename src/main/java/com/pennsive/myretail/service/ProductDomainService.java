package com.pennsive.myretail.service;

import com.pennsive.myretail.model.external.redsky.RedskyResponseV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductDomainService {	
	@Autowired
	private RestTemplate redskyRestTemplate;
	
	@Value("${redsky.product.full.url.v2}")
	private String redskyFullUrlV2;
	
	/*
	 * This sort of data retrieval would be a good candidate for caching, but the headers returned on the response explicitly forbid caching:
	 * 		cache-control:	max-age=0, no-cache, no-store
	 */
	@Async
	public CompletableFuture<ResponseEntity<RedskyResponseV2>> getProduct(Integer productId) {
	    return CompletableFuture.completedFuture(
	    		Optional.of(redskyRestTemplate.getForEntity(redskyFullUrlV2, RedskyResponseV2.class, productId)).get());
	}
}
