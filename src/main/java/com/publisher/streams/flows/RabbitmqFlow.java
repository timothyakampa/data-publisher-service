package com.publisher.streams.flows;

import akka.NotUsed;
import akka.stream.javadsl.Flow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.publisher.config.RabbitmqConfiguration;
import com.publisher.events.EventPublishError;
import com.publisher.events.EventPublished;
import com.publisher.events.ProductUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.util.Either;
import scala.util.Left;
import scala.util.Right;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class RabbitmqFlow {

    private static final int PARALLELISM = 8;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Flow<ProductUpdatedEvent, Either<EventPublishError, EventPublished>, NotUsed> publish() {
        return Flow.of(ProductUpdatedEvent.class)
                .mapAsync(PARALLELISM, event -> CompletableFuture.supplyAsync(() -> publishToRabbit(event)));
    }

    private Either<EventPublishError, EventPublished> publishToRabbit(ProductUpdatedEvent event) {
        try {
            String eventToPublish = event.asJsonString();
            log.info("Publishing event_type: {} message: {} to rabbit mq", event.getEventType(), eventToPublish);
            rabbitTemplate.convertAndSend(RabbitmqConfiguration.queueName, eventToPublish);
            return new Right<>(new EventPublished(eventToPublish));
        } catch (AmqpException e) {
            log.error("Failed to publish to rabbitmq", e);
            return new Left<>(new EventPublishError());
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize event to string", e);
            return new Left<>(new EventPublishError());
        }
    }
}
