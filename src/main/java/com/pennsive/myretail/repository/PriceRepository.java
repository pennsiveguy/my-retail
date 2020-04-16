package com.pennsive.myretail.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pennsive.myretail.document.PriceDocument;

public interface PriceRepository extends MongoRepository<PriceDocument, Long> {
}
