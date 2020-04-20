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
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.client.result.UpdateResult;
import com.pennsive.myretail.BaseTest;
import com.pennsive.myretail.IntegrationTestConfig;
import com.pennsive.myretail.document.PriceDocument;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@Import(IntegrationTestConfig.class)
@ActiveProfiles("dev")
@TestPropertySource("classpath:test-application-dev.properties")
public class PriceDocumentRepositoryIntegrationTest extends BaseTest {
	@Autowired
	private PriceDocumentRepository priceDocumentRepository;
	
	@Autowired
	private PriceDocumentUpdateRepository priceDocumentUpdateRepository;
	
	@Test
	public void testCrud() {
		Integer productId = nextInt();
		Double value = nextDouble();
		String currencyCode = randomAlphabetic(3);
		
		Optional<PriceDocument> price = priceDocumentRepository.findByProductId(productId);
		assertFalse(price.isPresent());
		
		PriceDocument newPrice = testObjectBuilder.buildPriceDocument(productId, new BigDecimal(value), currencyCode);
		
		priceDocumentRepository.save(newPrice);
		assertEquals(1, priceDocumentRepository.findAll().size());
		
		PriceDocument fetchedPrice = priceDocumentRepository.findByProductId(productId).get();
		assertTrue(value == fetchedPrice.getValue().doubleValue());
		assertEquals(currencyCode, fetchedPrice.getCurrencyCode());
		
		Double newValue = nextDouble();
		UpdateResult updateResult = priceDocumentUpdateRepository.updatePrice(productId, new BigDecimal(newValue));
		assertEquals(1l, updateResult.getModifiedCount());
		assertEquals(1, priceDocumentRepository.findAll().size());

		PriceDocument reFetchedPrice = priceDocumentRepository.findByProductId(productId).get();
		assertTrue(newValue == reFetchedPrice.getValue().doubleValue());
		assertEquals(1, priceDocumentRepository.findAll().size());
		
		priceDocumentRepository.delete(fetchedPrice);
		assertFalse(priceDocumentRepository.findByProductId(productId).isPresent());
		assertEquals(0, priceDocumentRepository.findAll().size());
	}
//	
//	@Configuration
//	public class config {
//		@Bean
//		public 
//	}
	
}
