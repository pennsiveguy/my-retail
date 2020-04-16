package com.pennsive.myretail.aggregator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.domain.ProductDomain;
import com.pennsive.myretail.response.Product;
import com.pennsive.myretail.service.PriceDocumentService;
import com.pennsive.myretail.service.ProductDomainService;

@RunWith(MockitoJUnitRunner.class)
public class ProductAggregatorTest {
	private Long productId;
	private String productName;
	private ProductDomain product;
	private BigDecimal value;
	private PriceDocument priceDocument;
	
	@Mock
	private ProductDomainService productService;
	
	@Mock
	private PriceDocumentService priceService;
	
	@InjectMocks
	private ProductAggregator subject = new ProductAggregator();
	
	@Before
	public void setUp() {
		productId = RandomUtils.nextLong();
		productName = RandomStringUtils.random(10);
		product = new ProductDomain(productId, productName);
		
		value = new BigDecimal(RandomUtils.nextDouble());
		priceDocument = new PriceDocument();
		priceDocument.setValue(value);
	}
	
	@Test
	public void getProduct_HappyPath() {
		when(productService.getProduct(productId)).thenReturn(product);
		when(priceService.getPrice(productId)).thenReturn(priceDocument);
		
		Product response = subject.getProduct(productId);
		
		assertEquals(productId, response.getId());
		assertEquals(value, response.getPrice().getValue());
		assertEquals(productName, response.getName());
		
		verify(productService).getProduct(productId);
		verify(priceService).getPrice(productId);
		verifyNoMoreInteractions(productService, priceService);
	}
}
