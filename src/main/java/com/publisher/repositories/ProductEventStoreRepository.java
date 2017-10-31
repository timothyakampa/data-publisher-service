package com.publisher.repositories;

import com.publisher.models.ProductEventStore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductEventStoreRepository extends CrudRepository<ProductEventStore, Long> {
}
