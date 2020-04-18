package com.pennsive.myretail.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
@EnableAutoConfiguration
public class PriceDocumentRepositoryIntegrationTest {
	@Autowired
	private PriceDocumentRepository priceDocumentRepository;
	
	

}
