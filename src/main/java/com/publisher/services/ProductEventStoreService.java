package com.publisher.services;

import com.publisher.models.ProductEventEntity;
import com.publisher.repositories.ProductEventEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.function.Function.identity;

@Service
public class ProductEventStoreService {

    @Autowired
    private ProductEventEntityRepository productEventEntityRepository;

    public List<ProductEventEntity> getAggregatedPendingEventEntities() {
        List<ProductEventEntity> productEventEntities = new ArrayList<>();
        StreamSupport
                .stream(productEventEntityRepository.findAll().spliterator(), true)
                .collect(Collectors.groupingBy(ProductEventEntity::getProductId))
                .forEach((productId, productEventEntityList) -> {
                    aggregate(productEventEntityList).ifPresent(productEventEntities::add);
                });
        return productEventEntities;
    }

    private Optional<ProductEventEntity> aggregate(List<ProductEventEntity> productEventEntities) {
        productEventEntities.sort(Comparator.comparing(ProductEventEntity::getId));
        return productEventEntities
                .stream()
                .map(Optional::of)
                .reduce((previousEvent, nextEvent) -> {
                    if (!previousEvent.isPresent()) return nextEvent;
                    if ("INSERT".equals(previousEvent.get().getEventType()) &&
                        "DELETE".equals(nextEvent.get().getEventType())) {
                        return Optional.empty();
                    }
                    return nextEvent;
                })
                .flatMap(identity());
    }

    /** Aggregation scenarios;
     *  Previous  Next
     *  INSERT -> INSERT; -> 2nd
     *  UPDATE -> UPDATE; -> 2nd
     *  DELETE -> DELETE; -> 2nd
     *  INSERT -> UPDATE; -> 2nd
     *  UPDATE -> INSERT; -> 2nd
     *  INSERT -> DELETE; -> nothing
     *  DELETE -> INSERT; -> 2nd
     *  UPDATE -> DELETE; -> 2nd
     *  DELETE -> UPDATE; -> 2nd
     */
}
