package com.pennsive.myretail.objectbuilder;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.math.BigDecimal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.pennsive.myretail.document.PriceDocument;
import com.pennsive.myretail.model.external.redsky.RedskyItem;
import com.pennsive.myretail.model.external.redsky.RedskyProduct;
import com.pennsive.myretail.model.external.redsky.RedskyProductDescription;
import com.pennsive.myretail.model.external.redsky.RedskyResponseV2;

public class TestObjectBuilder {	
	public ResponseEntity<RedskyResponseV2> buildRedskyResponse(String title) {
		RedskyProductDescription description = new RedskyProductDescription();
		ReflectionTestUtils.setField(description, "title", title);
		RedskyItem item = new RedskyItem();
		ReflectionTestUtils.setField(item, "productDescription", description);
		RedskyProduct product = new RedskyProduct();
		ReflectionTestUtils.setField(product, "item", item);
		RedskyResponseV2 response = new RedskyResponseV2();
		ReflectionTestUtils.setField(response, "product", product);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(randomAlphabetic(10));
		return new ResponseEntity<RedskyResponseV2>(response, headers, HttpStatus.OK);
	}
	
	public PriceDocument buildPriceDocument(Integer productId, BigDecimal value, String currencyCode) {
		PriceDocument price = new PriceDocument();
		ReflectionTestUtils.setField(price, "productId", productId);
		ReflectionTestUtils.setField(price, "value", value);
		ReflectionTestUtils.setField(price, "currencyCode", currencyCode);
		
		return price;
	}
	
	public HttpHeaders buildHeaders() {
		HttpHeaders headers = new HttpHeaders();
		
		for(int i = 0; i < 6; i++) {
			headers.add(randomAlphabetic(10), randomAlphabetic(10));
		}
		
		return headers;
	}
}
