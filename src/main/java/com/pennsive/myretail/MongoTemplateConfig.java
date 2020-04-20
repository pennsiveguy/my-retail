package com.pennsive.myretail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoTemplateConfig {
	@Value("${spring.data.mongodb.uri}")
	private String mongoDbUri;
	
	@Value("${spring.data.mongodb.database}")
	private String database;
	
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoDbUri);
    }
 
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient(), database);
    }
}
