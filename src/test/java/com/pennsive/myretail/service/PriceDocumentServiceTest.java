package com.pennsive.myretail.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextDouble;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.repository.PriceDocumentRepository;
import com.pennsive.myretail.response.PriceResponse;

@RunWith(MockitoJUnitRunner.class)
public class PriceDocumentServiceTest {
	private Integer productId;
	
	@Mock
	private PriceDocumentRepository priceRepository;
	
	@Mock
	private PriceDocument priceDocument;
	
	@InjectMocks
	private PriceDocumentService subject = new PriceDocumentService();
	
	@Before
	public void setUp() {
		productId = nextInt();
	}
	
	@Test
	public void getPrice_HappyPath() throws InterruptedException, ExecutionException {
		when(priceRepository.findByProductId(productId)).thenReturn(Optional.of(priceDocument));
		
		CompletableFuture<PriceDocument> actualResponse = subject.getPrice(productId);
		
		verify(priceRepository).findByProductId(productId);
		assertEquals(priceDocument, actualResponse.get());
	}
	
	@Test
	public void updatePrice_HappyPath() throws InterruptedException, ExecutionException {		
		when(priceRepository.findByProductId(productId)).thenReturn(Optional.of(priceDocument));		
		when(priceRepository.save(priceDocument)).thenReturn(priceDocument);
		
		PriceResponse newPrice = new PriceResponse(new BigDecimal(nextDouble()), randomAlphabetic(3));

		PriceDocument actualResponse = subject.updatePrice(productId, newPrice);
		
		verify(priceRepository).findByProductId(productId);
		verify(priceDocument).setValue(newPrice.getValue());
		verify(priceRepository).save(priceDocument);
		assertNotNull(actualResponse);
	}
}
