package com.pennsive.myretail.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pennsive.myretail.document.PriceDocument;

@Repository
public interface PriceDocumentRepository extends MongoRepository<PriceDocument, Integer> {
	Optional<PriceDocument> findByProductId(Integer productId);
}
