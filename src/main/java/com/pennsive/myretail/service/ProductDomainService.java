package com.pennsive.myretail.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pennsive.myretail.domain.ProductDomain;
import com.pennsive.myretail.model.external.redsky.RedskyResponseV2;

@Service
public class ProductDomainService {	
	@Autowired
	private RestTemplate redskyRestTemplate;
	
	@Value("${redsky.full.url.v2}")
	protected String redskyFullUrlV2;
	
	public ProductDomain getProduct(Long productId) {
	    RedskyResponseV2 redskyResponse = redskyRestTemplate.getForObject(redskyFullUrlV2, RedskyResponseV2.class, Collections.singletonMap("id", productId));
	    return new ProductDomain(productId, redskyResponse.getTitle());
	}
}
