package com.pennsive.myretail.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pennsive.myretail.aggregator.ProductAggregator;
import com.pennsive.myretail.response.PriceResponse;
import com.pennsive.myretail.response.ProductResponse;
import com.pennsive.myretail.service.PriceDocumentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextDouble;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerAdviceIntegrationTest {
	private static final String BASE_PATH = "/products/";

	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Mock
	private PriceDocumentService priceService;

	@Mock
	private ProductAggregator productAggregator;

	@InjectMocks
	private ProductController productController;

	private Integer id;
	private String url;
	private String name;
	private String currencyCode;
	private ResponseEntity<ProductResponse> response;
	private BigDecimal value;
	private PriceResponse priceResponse;

	@Before
	public void setup() {
		id = nextInt();
		url = BASE_PATH + id.toString();
		mockMvc = MockMvcBuilders.standaloneSetup(productController).setControllerAdvice(MyRetailControllerAdvice.class)
				.build();
		value = new BigDecimal(nextDouble());
		priceResponse = new PriceResponse();
		name = randomAlphabetic(10);
		currencyCode = randomAlphabetic(3);
		ReflectionTestUtils.setField(priceResponse, "value", value);
		ReflectionTestUtils.setField(priceResponse, "currencyCode", currencyCode);
		response = new ResponseEntity<ProductResponse>(new ProductResponse(id, name, priceResponse), HttpStatus.OK);
	}

	@Test
	public void getProduct_HappyPath() throws Exception {
		when(productAggregator.getProduct(id)).thenReturn(response);
		ResultActions resultActions = mockMvc.perform(get(url)).andExpect(status().isOk());

		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		ProductResponse actualResponse = objectMapper.readValue(contentAsString, ProductResponse.class);
		assertEquals(id, actualResponse.getId());
		assertEquals(name, actualResponse.getName());
		assertEquals(priceResponse.getValue(), actualResponse.getPrice().getValue());
		assertEquals(priceResponse.getCurrencyCode(), actualResponse.getPrice().getCurrencyCode());
	}

	@Test
	public void getProduct_InvalidProductId() throws Exception {
		mockMvc.perform(get(BASE_PATH + randomAlphabetic(5))).andExpect(status().isBadRequest());
	}

	@Test
	public void getProduct_MissingProductId() throws Exception {
		mockMvc.perform(get(BASE_PATH)).andExpect(status().isNotFound());
	}

	@Test
	public void getProduct_NoProductFound() throws Exception {
		when(productAggregator.getProduct(id)).thenThrow(new NoSuchElementException());
		mockMvc.perform(get(url)).andExpect(status().isNotFound());
	}

	@Test
	public void getProduct_ErrorCallingRedsky() throws Exception {
		when(productAggregator.getProduct(id)).thenThrow(new RestClientException(null));
		mockMvc.perform(get(url)).andExpect(status().isNotFound());
	}

	@Test
	public void updatePrice_HappyPath() throws Exception {
		mockMvc.perform(put(url).content(objectMapper.writeValueAsString(priceResponse))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		ArgumentCaptor<PriceResponse> priceRespCapture = ArgumentCaptor.forClass(PriceResponse.class);
		verify(priceService).updatePrice(eq(id), priceRespCapture.capture());
		PriceResponse updatedPrice = priceRespCapture.getValue();
		assertEquals(priceResponse.getValue(), updatedPrice.getValue());
	}

	@Test
	public void updatePrice_InvalidProductId() throws Exception {
		mockMvc.perform(put(BASE_PATH + randomAlphabetic(5)).content(objectMapper.writeValueAsString(priceResponse))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void updatePrice_InvalidPrice() throws Exception {
		PriceResponse invalidPrice = new PriceResponse(new BigDecimal(-1.00), randomAlphabetic(10));
		mockMvc.perform(put(BASE_PATH + randomAlphabetic(5)).content(objectMapper.writeValueAsString(invalidPrice))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void updatePrice_PriceNotFound() throws Exception {
		when(priceService.updatePrice(eq(id), any(PriceResponse.class))).thenThrow(new NoSuchElementException());

		mockMvc.perform(put(url).content(objectMapper.writeValueAsString(priceResponse))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
		ArgumentCaptor<PriceResponse> priceRespCapture = ArgumentCaptor.forClass(PriceResponse.class);
		verify(priceService).updatePrice(eq(id), priceRespCapture.capture());
		PriceResponse updatedPrice = priceRespCapture.getValue();
		assertEquals(priceResponse.getValue(), updatedPrice.getValue());
	}
}
