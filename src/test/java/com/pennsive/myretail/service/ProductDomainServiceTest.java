package com.pennsive.myretail.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pennsive.myretail.model.external.redsky.Item;
import com.pennsive.myretail.model.external.redsky.Product;
import com.pennsive.myretail.model.external.redsky.ProductDescription;
import com.pennsive.myretail.model.external.redsky.RedskyResponseV2;
import com.pennsive.myretail.objectbuilder.TestObjectBuilder;

@RunWith(MockitoJUnitRunner.class)
public class ProductDomainServiceTest {
	private Long productId;
	private String title;
	private RedskyResponseV2 redskyResponse;
	private String redskyUrl;
	
	private TestObjectBuilder builder = new TestObjectBuilder();
	
	@Mock
	private RestTemplate redskyRestTemplate;
	
	@Mock
	private ObjectMapper objectMapper;
	
	@InjectMocks
	private ProductDomainService subject = new ProductDomainService();
	
	@Before
	public void setUp() {
		redskyUrl = RandomStringUtils.random(10);
		ReflectionTestUtils.setField(subject, "redskyFullUrlV2", redskyUrl);
		productId = RandomUtils.nextLong();
		title = RandomStringUtils.random(10);
		redskyResponse = builder.buildRedskyResponse(title);
	}
	
	@Test
	public void getProduct_HappyPath() throws InterruptedException, ExecutionException {
		when(redskyRestTemplate.getForObject(redskyUrl, RedskyResponseV2.class, productId)).thenReturn(redskyResponse);
		
		RedskyResponseV2 result = subject.getProduct(productId).get();
		assertEquals(title, result.getTitle());
		
		verify(redskyRestTemplate).getForObject(redskyUrl, RedskyResponseV2.class, productId);
	}
	
	@Test(expected = RestClientException.class)
	public void getProduct_RestClientException() {
		when(redskyRestTemplate.getForObject(redskyUrl, RedskyResponseV2.class, productId)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_GATEWAY));
		subject.getProduct(productId);
	}
}
