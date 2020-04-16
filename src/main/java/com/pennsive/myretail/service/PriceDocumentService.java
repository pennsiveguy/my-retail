package com.pennsive.myretail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.repository.PriceRepository;
import com.pennsive.myretail.response.Price;

@Service
public class PriceDocumentService {
	@Autowired
	private PriceRepository priceRepository;

	public PriceDocument getPrice(Long productId) {
		return priceRepository.findById(productId).get();
	}

	public void updatePrice(Long productId, Price price) {
		PriceDocument priceDocument = getPrice(productId);
		priceDocument.setValue(price.getValue());
		priceRepository.save(priceDocument);
	}
}
