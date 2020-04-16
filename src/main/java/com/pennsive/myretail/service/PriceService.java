package com.pennsive.myretail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pennsive.myretail.document.Price;
import com.pennsive.myretail.repository.PriceRepository;

@Service
public class PriceService {
	@Autowired
	private PriceRepository priceRepository;
	

	public Price getPrice(Long productId) {
		return priceRepository.findById(productId).get();
	}
}
