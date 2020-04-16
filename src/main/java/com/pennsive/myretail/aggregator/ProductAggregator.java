package com.pennsive.myretail.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.domain.ProductDomain;
import com.pennsive.myretail.response.Product;
import com.pennsive.myretail.service.PriceDocumentService;
import com.pennsive.myretail.service.ProductDomainService;

@Service
public class ProductAggregator {
	@Autowired
	private ProductDomainService productDomainService;

	@Autowired
	private PriceDocumentService priceDocumentService;
	
	public Product getProduct(Long productId){
		
		ProductDomain productDomain = productDomainService.getProduct(productId);
		
		PriceDocument priceDocument = priceDocumentService.getPrice(productId);
		
		return new Product(productDomain, priceDocument);
	}
}
