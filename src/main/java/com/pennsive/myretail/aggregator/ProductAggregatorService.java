package com.pennsive.myretail.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.domain.ProductDomain;
import com.pennsive.myretail.response.Product;
import com.pennsive.myretail.service.PriceDocumentService;
import com.pennsive.myretail.service.ProductDomainService;

@Service
public class ProductAggregatorService {
	@Autowired
	private ProductDomainService productDomainService;

	@Autowired
	private PriceDocumentService priceDocumentService;
	
	public Product getProduct(Long productId){
		
		ProductDomain productDomain = null;
		productDomain = productDomainService.getProduct(productId);
		
		PriceDocument priceDocument = priceDocumentService.getPrice(productId);
		
		return aggregateProduct(productDomain, priceDocument);
	}
	
	protected Product aggregateProduct(ProductDomain productDomain, PriceDocument priceDocument) {
		return new Product(productDomain, priceDocument);
	}
}
