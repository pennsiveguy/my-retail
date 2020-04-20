package com.pennsive.myretail.aggregator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.pennsive.myretail.BaseTest;
import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.model.external.redsky.RedskyResponseV2;
import com.pennsive.myretail.response.ProductResponse;
import com.pennsive.myretail.service.PriceDocumentService;
import com.pennsive.myretail.service.ProductDomainService;

@RunWith(MockitoJUnitRunner.class)
public class ProductAggregatorTest extends BaseTest {
	private Integer productId;
	private String productName;
	private RedskyResponseV2 redskyResponse;
	private BigDecimal value;
	private PriceDocument priceDocument;
	private String currencyCode;
	
	@Mock
	private ProductDomainService productService;
	
	@Mock
	private PriceDocumentService priceService;
	
	@InjectMocks
	private ProductAggregator subject = new ProductAggregator();
	
	@Before
	public void setUp() {
		productId = RandomUtils.nextInt();
		productName = RandomStringUtils.random(10);
		redskyResponse = testObjectBuilder.buildRedskyResponse(productName);
		
		value = new BigDecimal(RandomUtils.nextDouble());
		currencyCode = RandomStringUtils.random(10);
		priceDocument = testObjectBuilder.buildPriceDocument(productId, value, currencyCode);
	}
	
	@Test
	public void getProduct_HappyPath() {
		when(productService.getProduct(productId)).thenReturn(CompletableFuture.completedFuture(Optional.of(redskyResponse).get()));
		when(priceService.getPrice(productId)).thenReturn(CompletableFuture.completedFuture(priceDocument));
		
		ProductResponse response = subject.getProduct(productId);
		
		assertEquals(productId, response.getId());
		assertEquals(value, response.getPrice().getValue());
		assertEquals(productName, response.getName());
		
		verify(productService).getProduct(productId);
		verify(priceService).getPrice(productId);
		verifyNoMoreInteractions(productService, priceService);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void getProduct_InterruptedException_FromProduct() throws InterruptedException, ExecutionException {
		@SuppressWarnings("unchecked")
		CompletableFuture<RedskyResponseV2> mockFuture = Mockito.mock(CompletableFuture.class);
		when(productService.getProduct(productId)).thenReturn(mockFuture);
		when(mockFuture.get()).thenThrow(new InterruptedException());
		subject.getProduct(productId);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void getProduct_ExecutionException_FromProduct() throws InterruptedException, ExecutionException {
		@SuppressWarnings("unchecked")
		CompletableFuture<RedskyResponseV2> mockFuture = Mockito.mock(CompletableFuture.class);
		when(productService.getProduct(productId)).thenReturn(mockFuture);
		when(mockFuture.get()).thenThrow(new TestException());
		subject.getProduct(productId);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void getProduct_InterruptedException_FromPrice() throws InterruptedException, ExecutionException {
		@SuppressWarnings("unchecked")
		CompletableFuture<PriceDocument> mockFuture = Mockito.mock(CompletableFuture.class);
		when(productService.getProduct(productId)).thenReturn(CompletableFuture.completedFuture(redskyResponse));
		when(priceService.getPrice(productId)).thenReturn(mockFuture);
		when(mockFuture.get()).thenThrow(new InterruptedException());
		subject.getProduct(productId);
	}
	
	@Test(expected = NoSuchElementException.class)
	public void getProduct_ExecutionException_FromPrice() throws InterruptedException, ExecutionException {
		@SuppressWarnings("unchecked")
		CompletableFuture<PriceDocument> mockFuture = Mockito.mock(CompletableFuture.class);
		when(productService.getProduct(productId)).thenReturn(CompletableFuture.completedFuture(redskyResponse));
		when(priceService.getPrice(productId)).thenReturn(mockFuture);
		when(mockFuture.get()).thenThrow(new TestException());
		subject.getProduct(productId);
	}
	
	@SuppressWarnings("serial")
	class TestException extends ExecutionException {
		protected TestException() {
			super();
		}
	}
}
