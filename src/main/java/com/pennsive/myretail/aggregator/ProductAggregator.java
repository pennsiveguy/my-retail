package com.pennsive.myretail.aggregator;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	public ResponseEntity<ProductResponse> getProduct(Integer productId) {
		CompletableFuture<ResponseEntity<RedskyResponseV2>> redskyResponseFuture = productDomainService.getProduct(productId);

		CompletableFuture<PriceDocument> priceDocumentFuture = priceDocumentService.getPrice(productId);

		ResponseEntity<RedskyResponseV2> redskyResponse = null;
		PriceDocument priceDocument = null;
		try {
			redskyResponse = redskyResponseFuture.get();
			priceDocument = priceDocumentFuture.get();
		} catch (InterruptedException | ExecutionException ex) {
			throw new NoSuchElementException();
		}		

		ProductResponse productResponse = new ProductResponse(productId, redskyResponse.getBody().getTitle(), 
				new PriceResponse(priceDocument.getValue(), priceDocument.getCurrencyCode()));
		
		HttpHeaders responseHeaders = HttpHeaders.writableHttpHeaders(new HttpHeaders());
		responseHeaders.setCacheControl(redskyResponse.getHeaders().getCacheControl());
		
		ResponseEntity<ProductResponse> productResponseEntity = new ResponseEntity<ProductResponse>(productResponse, responseHeaders, HttpStatus.OK);
		
		return productResponseEntity;
	}
}
