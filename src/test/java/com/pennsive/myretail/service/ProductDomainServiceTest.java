package com.pennsive.myretail.service;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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

import com.pennsive.myretail.model.external.redsky.RedskyProduct;
import com.pennsive.myretail.model.external.redsky.RedskyResponseV2;

@RunWith(MockitoJUnitRunner.class)
public class ProductDomainServiceTest {
	private Integer productId;
	private String redskyUrl;
	
	@Mock
	private RestTemplate redskyRestTemplate;
	
	@Mock
	private RedskyResponseV2 redskyResponse;
	
	@InjectMocks
	private ProductDomainService subject = new ProductDomainService();
	
	@Before
	public void setUp() {
		redskyUrl = RandomStringUtils.random(10);
		ReflectionTestUtils.setField(subject, "redskyFullUrlV2", redskyUrl);
		productId = RandomUtils.nextInt();
	}
	
	@Test
	public void getProduct_HappyPath() throws InterruptedException, ExecutionException {
		when(redskyRestTemplate.getForObject(redskyUrl, RedskyResponseV2.class, productId)).thenReturn(redskyResponse);
		
		RedskyResponseV2 actualResponse = subject.getProduct(productId).get();
		assertSame(redskyResponse, actualResponse);
		
		verify(redskyRestTemplate).getForObject(redskyUrl, RedskyResponseV2.class, productId);
		verify(redskyResponse, never()).setProduct(any(RedskyProduct.class));
		verify(redskyResponse, never()).getTitle();
	}
	
	@Test(expected = RestClientException.class)
	public void getProduct_RestClientException() {
		when(redskyRestTemplate.getForObject(redskyUrl, RedskyResponseV2.class, productId)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_GATEWAY));
		subject.getProduct(productId);
	}
}