package com.pennsive.myretail.repository;

import com.pennsive.myretail.document.PriceDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceDocumentRepository extends MongoRepository<PriceDocument, Integer> {
	Optional<PriceDocument> findByProductId(Integer productId);
}
