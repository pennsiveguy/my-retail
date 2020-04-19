package com.pennsive.myretail.objectbuilder;

import org.springframework.test.util.ReflectionTestUtils;

import com.pennsive.myretail.model.external.redsky.RedskyItem;
import com.pennsive.myretail.model.external.redsky.RedskyProduct;
import com.pennsive.myretail.model.external.redsky.RedskyProductDescription;
import com.pennsive.myretail.model.external.redsky.RedskyResponseV2;

public class TestObjectBuilder {	
	public RedskyResponseV2 buildRedskyResponse(String title) {
		RedskyProductDescription description = new RedskyProductDescription();
		ReflectionTestUtils.setField(description, "title", title);
		RedskyItem item = new RedskyItem();
		ReflectionTestUtils.setField(item, "productDescription", description);
		RedskyProduct product = new RedskyProduct();
		ReflectionTestUtils.setField(product, "item", item);
		RedskyResponseV2 response = new RedskyResponseV2();
		ReflectionTestUtils.setField(response, "product", product);
		return response;
	}
}
