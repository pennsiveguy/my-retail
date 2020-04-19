package com.pennsive.myretail.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextDouble;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pennsive.myretail.aggregator.ProductAggregator;
import com.pennsive.myretail.response.PriceResponse;
import com.pennsive.myretail.response.ProductResponse;
import com.pennsive.myretail.service.PriceDocumentService;

@SpringBootTest
@AutoConfigureMockMvc
public class WebMockTest {
	private static final String BASE_PATH = "/products/";
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductAggregator productAggregator;

	@MockBean
	private PriceDocumentService priceService;
	
	private Integer id;
	private String url;
	private String name;
	private String currencyCode;
	private ProductResponse response;
	private BigDecimal value;
	private PriceResponse priceResponse;
	
	@BeforeEach
	public void setUp() {
		id = nextInt();
		url = BASE_PATH + id.toString();
		value = new BigDecimal(nextDouble());
		priceResponse = new PriceResponse();
		name = randomAlphabetic(10);
		currencyCode = randomAlphabetic(3);
		ReflectionTestUtils.setField(priceResponse, "value", value);
		ReflectionTestUtils.setField(priceResponse, "currencyCode", currencyCode);
		response = new ProductResponse(id, name, priceResponse);
	}

	@Test
	public void getProduct_HappyPath() throws Exception {
		when(productAggregator.getProduct(id)).thenReturn(response);
		ResultActions resultActions = this.mockMvc.perform(get(url)).andExpect(status().isOk());
		
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
		this.mockMvc.perform(get(BASE_PATH + randomAlphabetic(5))).andExpect(status().isBadRequest());
	}

	@Test
	public void getProduct_MissingProductId() throws Exception {
		this.mockMvc.perform(get(BASE_PATH)).andExpect(status().isNotFound());
	}

	@Test
	public void updatePrice_HappyPath() throws Exception {
		this.mockMvc.perform(
			      put(url)
			      .content(objectMapper.writeValueAsString(priceResponse))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());

		ArgumentCaptor<PriceResponse> priceRespCapture = ArgumentCaptor.forClass(PriceResponse.class);
		verify(priceService).updatePrice(eq(id), priceRespCapture.capture());
		PriceResponse updatedPrice = priceRespCapture.getValue();
		assertEquals(priceResponse.getValue(), updatedPrice.getValue());
	}
}
