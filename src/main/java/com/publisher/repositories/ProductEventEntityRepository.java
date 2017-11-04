package com.publisher.repositories;

import com.publisher.models.ProductEventEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductEventEntityRepository extends CrudRepository<ProductEventEntity, Long> {
}
