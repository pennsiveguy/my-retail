package com.pennsive.myretail.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.repository.PriceDocumentRepository;
import com.pennsive.myretail.repository.PriceDocumentUpdateRepository;
import com.pennsive.myretail.response.PriceResponse;

@Service
public class PriceDocumentService {
	@Autowired
	private PriceDocumentRepository priceDocumentRepository;
	
	@Autowired
	private PriceDocumentUpdateRepository priceDocumentUpdateRepository;

	@Async
	public CompletableFuture<PriceDocument> getPrice(Integer productId) {
		return CompletableFuture.completedFuture(priceDocumentRepository.findByProductId(productId).get());
	}

	public PriceDocument updatePrice(Integer productId, PriceResponse price) {
		priceDocumentRepository.findByProductId(productId).get();
		priceDocumentUpdateRepository.updatePrice(productId, price.getValue());
		return priceDocumentRepository.findByProductId(productId).get();
	}
}
