package com.pennsive.myretail.objectbuilder;

import com.pennsive.myretail.model.external.redsky.RedskyItem;
import com.pennsive.myretail.model.external.redsky.RedskyProduct;
import com.pennsive.myretail.model.external.redsky.RedskyProductDescription;
import com.pennsive.myretail.model.external.redsky.RedskyResponseV2;

public class TestObjectBuilder {	
	public RedskyResponseV2 buildRedskyResponse(String title) {
		RedskyProductDescription description = new RedskyProductDescription();
		description.setTitle(title);
		RedskyItem item = new RedskyItem();
		item.setProductDescription(description);
		RedskyProduct product = new RedskyProduct();
		product.setItem(item);

		RedskyResponseV2 response = new RedskyResponseV2();
		response.setProduct(product);
		return response;
	}
}
