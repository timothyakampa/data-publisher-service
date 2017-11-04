package com.publisher.streams.flows;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import com.publisher.events.ProductEvent;
import com.publisher.models.ProductEventEntity;
import com.publisher.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class MessageTranslationFlow {

    private static final int PARALLELISM = 8;

    @Autowired
    private ProductRepository productRepository;

    public Flow<ProductEventEntity, ProductEvent, NotUsed> toEvents() {
        return Flow.of(ProductEventEntity.class)
                .mapAsync(PARALLELISM, event -> CompletableFuture.supplyAsync(() -> translate(event)));
    }

    private ProductEvent translate(ProductEventEntity event) {
        return new ProductEvent(
                event.getEventType(),
                productRepository.findOne(event.getProductId())
        );
    }
}

