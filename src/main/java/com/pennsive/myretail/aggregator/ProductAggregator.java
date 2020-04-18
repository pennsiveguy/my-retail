package com.pennsive.myretail.aggregator;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.domain.ProductDomain;
import com.pennsive.myretail.model.external.redsky.RedskyResponseV2;
import com.pennsive.myretail.response.Product;
import com.pennsive.myretail.service.PriceDocumentService;
import com.pennsive.myretail.service.ProductDomainService;

@Service
public class ProductAggregator {
	@Autowired
	private ProductDomainService productDomainService;

	@Autowired
	private PriceDocumentService priceDocumentService;

	public Product getProduct(Long productId) {

		CompletableFuture<RedskyResponseV2> productDomainFuture = productDomainService.getProduct(productId);

		CompletableFuture<PriceDocument> priceDocumentFuture = priceDocumentService.getPrice(productId);

		ProductDomain productDomain = null;
		PriceDocument priceDocument = null;
		try {
			productDomain = new ProductDomain(productId, productDomainFuture.get().getTitle());
			priceDocument = priceDocumentFuture.get();
		} catch (InterruptedException | ExecutionException ex) {
			throw new NoSuchElementException();
		}

		return new Product(productDomain, priceDocument);
	}
}
