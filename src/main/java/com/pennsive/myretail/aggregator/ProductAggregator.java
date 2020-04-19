package com.pennsive.myretail.aggregator;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.model.external.redsky.RedskyResponseV2;
import com.pennsive.myretail.response.PriceResponse;
import com.pennsive.myretail.response.ProductResponse;
import com.pennsive.myretail.service.PriceDocumentService;
import com.pennsive.myretail.service.ProductDomainService;

@Service
public class ProductAggregator {
	@Autowired
	private ProductDomainService productDomainService;

	@Autowired
	private PriceDocumentService priceDocumentService;

	public ProductResponse getProduct(Integer productId) {

		CompletableFuture<RedskyResponseV2> redskyResponseFuture = productDomainService.getProduct(productId);

		CompletableFuture<PriceDocument> priceDocumentFuture = priceDocumentService.getPrice(productId);

		RedskyResponseV2 redskyResponse = null;
		PriceDocument priceDocument = null;
		try {
			redskyResponse = redskyResponseFuture.get();
			priceDocument = priceDocumentFuture.get();
		} catch (InterruptedException | ExecutionException ex) {
			throw new NoSuchElementException();
		}

		return new ProductResponse(productId, redskyResponse.getTitle(), 
				new PriceResponse(priceDocument.getValue(), priceDocument.getCurrencyCode()));
	}
}
