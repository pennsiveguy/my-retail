package com.pennsive.myretail.repository;

import com.mongodb.client.result.UpdateResult;
import com.pennsive.myretail.document.PriceDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class PriceDocumentUpdateRepository {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public UpdateResult updatePrice(Integer productId, BigDecimal newPrice) {
		Query query = new Query();
		query.addCriteria(Criteria.where("productId").is(productId));
		Update update = new Update();
		update.set("value", newPrice);
		return mongoTemplate.updateFirst(query, update, PriceDocument.class);
	}	
}
