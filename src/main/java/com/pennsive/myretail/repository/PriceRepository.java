package com.pennsive.myretail.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pennsive.myretail.document.Price;

public interface PriceRepository extends MongoRepository<Price, Long> {
}
