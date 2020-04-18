package com.pennsive.myretail.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.repository.PriceRepository;
import com.pennsive.myretail.response.Price;

@Service
public class PriceDocumentService {
	@Autowired
	private PriceRepository priceRepository;

	@Async
	public CompletableFuture<PriceDocument> getPrice(Long productId) {
		return CompletableFuture.completedFuture(priceRepository.findById(productId).get());
	}

	public void updatePrice(Long productId, Price price) {
		PriceDocument priceDocument = null;
		try {
			priceDocument = getPrice(productId).get();
		} catch (InterruptedException | ExecutionException e) {
			
		}
		priceDocument.setValue(price.getValue());
		priceRepository.save(priceDocument);
	}
}
