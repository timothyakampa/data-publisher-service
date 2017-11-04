package com.publisher.streams;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.publisher.services.ProductEventStoreService;
import com.publisher.streams.flows.MessageTranslationFlow;
import com.publisher.streams.flows.RabbitmqPublisherFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.concurrent.duration.FiniteDuration;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**

 Runnable graph for publishing events on rabbit mq
 ===================================================
 Source<ProductEventEntity, NotUsed>
 Flow<ProductEventEntity, ProductEvent, NotUsed> --> Sink or Retry Flow
 Flow<ProductEvent, Either[PublishError, Published], NotUsed> --> Sink or Retry Flow
 Sink<Stored, Future[Done]>

 ***/


@Slf4j
@Component
public class ProductDataPublisherGraph {

    private static final FiniteDuration INITIAL_DELAY = new FiniteDuration(0, TimeUnit.SECONDS);
    private static final FiniteDuration INTERVAL = new FiniteDuration(5, TimeUnit.SECONDS);
    private static final int TICK = 1;

    @Autowired
    private ProductEventStoreService productEventStoreService;

    @Autowired
    private RabbitmqPublisherFlow rabbitmqFlow;

    @Autowired
    private MessageTranslationFlow messageTranslationFlow;

    public void run() {
        final ActorSystem system = ActorSystem.create("data-publisher");
        final Materializer materializer = ActorMaterializer.create(system);

        // product data sync runnable graph
        Source.tick(INITIAL_DELAY, INTERVAL, TICK)
                .mapConcat(t -> {
                    log.info("\nReceiving tick from source at {}\n", LocalDateTime.now().toString());
                    return productEventStoreService.getAggregatedPendingEventEntities();
                })
                .via(messageTranslationFlow.toEvents())
                .via(rabbitmqFlow.publish())
                .runWith(Sink.ignore(), materializer);
    }
}
