package com.pennsive.myretail.controller;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.apache.commons.lang3.RandomUtils.nextDouble;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pennsive.myretail.aggregator.ProductAggregator;
import com.pennsive.myretail.response.PriceResponse;
import com.pennsive.myretail.response.ProductResponse;
import com.pennsive.myretail.service.PriceDocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@AutoConfigureMockMvc
public class WebMockTest {
	private static final String BASE_PATH = "/products/";
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductAggregator aggregator;

	@MockBean
	private PriceDocumentService priceService;
	
	private Integer id;
	private String url;
	private String name;
	private String currencyCode;
	private ProductResponse response;
	private BigDecimal value;
	private PriceResponse price;
	
	@BeforeEach
	public void setUp() {
		id = nextInt();
		url = BASE_PATH + id.toString();
		value = new BigDecimal(nextDouble());
		price = new PriceResponse();
		name = randomAlphabetic(10);
		currencyCode = randomAlphabetic(3);
		ReflectionTestUtils.setField(price, "value", value);
		ReflectionTestUtils.setField(price, "currencyCode", currencyCode);
		response = new ProductResponse(id, name, price);
	}

	@Test
	public void getProduct_HappyPath() throws Exception {
		when(aggregator.getProduct(id)).thenReturn(response);
		ResultActions resultActions = this.mockMvc.perform(get(url)).andExpect(status().isOk());
		
		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		ProductResponse actualResponse = objectMapper.readValue(contentAsString, ProductResponse.class);
		assertEquals(id, actualResponse.getId());
		assertEquals(name, actualResponse.getName());
		assertEquals(price.getValue(), actualResponse.getPrice().getValue());
		assertEquals(price.getCurrencyCode(), actualResponse.getPrice().getCurrencyCode());
	}

	@Test
	public void getProduct_InvalidProductId() throws Exception {
		when(aggregator.getProduct(id)).thenReturn(response);
		this.mockMvc.perform(get(BASE_PATH + randomAlphabetic(5))).andExpect(status().isBadRequest());
	}

	@Test
	public void getProduct_MissingProductId() throws Exception {
		when(aggregator.getProduct(id)).thenReturn(response);
		this.mockMvc.perform(get(BASE_PATH)).andExpect(status().isNotFound());
	}

	@Test
	public void getProduct_NoSuchElementException() throws Exception {
		when(aggregator.getProduct(id)).thenThrow(new NoSuchElementException());
		this.mockMvc.perform(get(BASE_PATH)).andExpect(status().isNotFound());
	}
}
