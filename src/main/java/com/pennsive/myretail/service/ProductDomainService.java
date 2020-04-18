package com.pennsive.myretail.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pennsive.myretail.model.external.redsky.RedskyResponseV2;

@Service
public class ProductDomainService {	
	@Autowired
	private RestTemplate redskyRestTemplate;
	
	@Value("${redsky.full.url.v2}")
	private String redskyFullUrlV2;
	
	/*
	 * This sort of data retrieval would be a good candidate for caching, but the headers returned on the response explicitly forbid caching:
	 * 		cache-control:	max-age=0, no-cache, no-store
	 */
	@Async
	public CompletableFuture<RedskyResponseV2> getProduct(Long productId) {
	    return CompletableFuture.completedFuture(redskyRestTemplate.getForObject(redskyFullUrlV2, RedskyResponseV2.class, productId));
	}
}
