package com.pennsive.myretail.repository;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextDouble;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.repository.PriceDocumentRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
public class PriceDocumentRepositoryIntegrationTest {
	@Autowired
	private PriceDocumentRepository repository;
	
	@Test
	public void testCrud() {
		Integer productId = nextInt();
		Double value = nextDouble();
		String currencyCode = randomAlphabetic(3);
		
		Optional<PriceDocument> price = repository.findByProductId(productId);
		assertFalse(price.isPresent());
		
		PriceDocument newPrice = new PriceDocument();
		newPrice.setValue(new BigDecimal(value));
		ReflectionTestUtils.setField(newPrice, "productId", productId);
		ReflectionTestUtils.setField(newPrice, "currencyCode", currencyCode);
		
		repository.save(newPrice);
		assertEquals(1, repository.findAll().size());
		
		PriceDocument fetchedPrice = repository.findByProductId(productId).get();
		assertTrue(value == fetchedPrice.getValue().doubleValue());
		assertEquals(currencyCode, fetchedPrice.getCurrencyCode());
		
		Double newValue = nextDouble();
		fetchedPrice.setValue(new BigDecimal(newValue));
		String newCurrencyCode = randomAlphabetic(3);
		ReflectionTestUtils.setField(fetchedPrice, "currencyCode", newCurrencyCode);
		
		repository.save(fetchedPrice);
		assertEquals(1, repository.findAll().size());

		PriceDocument reFetchedPrice = repository.findByProductId(productId).get();
		assertTrue(newValue == reFetchedPrice.getValue().doubleValue());
		assertEquals(newCurrencyCode, reFetchedPrice.getCurrencyCode());
		assertEquals(1, repository.findAll().size());
		
		repository.delete(fetchedPrice);
		assertFalse(repository.findByProductId(productId).isPresent());
		assertEquals(0, repository.findAll().size());
	}
}
