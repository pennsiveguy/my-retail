package com.pennsive.myretail.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.repository.PriceDocumentRepository;
import com.pennsive.myretail.response.PriceResponse;

@Service
public class PriceDocumentService {
	@Autowired
	private PriceDocumentRepository priceDocumentRepository;

	@Async
	public CompletableFuture<PriceDocument> getPrice(Integer productId) {
		return CompletableFuture.completedFuture(priceDocumentRepository.findByProductId(productId).get());
	}

	public void updatePrice(Integer productId, PriceResponse price) {
		PriceDocument priceDocument = priceDocumentRepository.findByProductId(productId).get();
		priceDocument.setValue(price.getValue());
		priceDocumentRepository.save(priceDocument);
	}
}
