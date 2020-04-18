package com.pennsive.myretail.objectbuilder;

import com.pennsive.myretail.model.external.redsky.Item;
import com.pennsive.myretail.model.external.redsky.Product;
import com.pennsive.myretail.model.external.redsky.ProductDescription;
import com.pennsive.myretail.model.external.redsky.RedskyResponseV2;

public class TestObjectBuilder {	
	public RedskyResponseV2 buildRedskyResponse(String title) {
		ProductDescription description = new ProductDescription();
		description.setTitle(title);
		Item item = new Item();
		item.setProductDescription(description);
		Product product = new Product();
		product.setItem(item);

		RedskyResponseV2 response = new RedskyResponseV2();
		response.setProduct(product);
		return response;
	}
}
