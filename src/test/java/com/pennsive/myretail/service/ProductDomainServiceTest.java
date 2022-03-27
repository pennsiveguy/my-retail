package com.pennsive.myretail.service;

import com.pennsive.myretail.BaseTest;
import com.pennsive.myretail.model.external.redsky.RedskyResponseV2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDomainServiceTest extends BaseTest{
	private Integer productId;
	private String redskyUrl;
	private HttpHeaders headers;
	
	@Mock
	private RestTemplate redskyRestTemplate;
	
	@Mock
	private RedskyResponseV2 redskyResponse;
	
	@Mock
	private ResponseEntity<RedskyResponseV2> responseEntity;
	
	@InjectMocks
	private ProductDomainService subject = new ProductDomainService();
	
	@Before
	public void setUp() {
		redskyUrl = RandomStringUtils.random(10);
		ReflectionTestUtils.setField(subject, "redskyFullUrlV2", redskyUrl);
		productId = RandomUtils.nextInt();
		headers = testObjectBuilder.buildHeaders();
	}
	
	@Test
	public void getProduct_HappyPath() throws InterruptedException, ExecutionException {
		when(redskyRestTemplate.getForEntity(redskyUrl, RedskyResponseV2.class, productId)).thenReturn(responseEntity);
		when(responseEntity.getBody()).thenReturn(redskyResponse);
		when(responseEntity.getHeaders()).thenReturn(headers);
		
		ResponseEntity<RedskyResponseV2> actualResponseEntity = subject.getProduct(productId).get();
		assertSame(responseEntity.getBody(), actualResponseEntity.getBody());
		
		verify(redskyRestTemplate).getForEntity(redskyUrl, RedskyResponseV2.class, productId);
		assertSame(headers, actualResponseEntity.getHeaders());
	}
	
	@Test(expected = RestClientException.class)
	public void getProduct_RestClientException() {
		when(redskyRestTemplate.getForEntity(redskyUrl, RedskyResponseV2.class, productId)).thenThrow(new HttpClientErrorException(HttpStatus.BAD_GATEWAY));
		subject.getProduct(productId);
	}
}
