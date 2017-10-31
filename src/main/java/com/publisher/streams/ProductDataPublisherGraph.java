package com.publisher.streams;

import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.publisher.repositories.ProductEventStoreRepository;
import com.publisher.streams.flows.MessageTranslationFlow;
import com.publisher.streams.flows.RabbitmqPublisherFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

/**

 Runnable graph for publishing events on rabbit mq
 ===================================================
 Source<ProductEventStore, NotUsed>
 Flow<ProductEventStore, ProductEvent, NotUsed> --> Sink or Retry Flow
 Flow<ProductEvent, Either[PublishError, Published], NotUsed> --> Sink or Retry Flow
 Sink<Stored, Future[Done]>

 ***/


@Slf4j
@Component
public class ProductDataPublisherGraph {

    private static final FiniteDuration INITIAL_DELAY = new FiniteDuration(0, TimeUnit.SECONDS);
    private static final FiniteDuration INTERVAL = new FiniteDuration(10, TimeUnit.SECONDS);
    private static final int TICK = 1;

    @Autowired
    private ProductEventStoreRepository eventStoreRepository;

    @Autowired
    private RabbitmqPublisherFlow rabbitmqFlow;

    @Autowired
    private MessageTranslationFlow messageTranslationFlow;

    public void run() {
        final ActorSystem system = ActorSystem.create("data-publisher");
        final Materializer materializer = ActorMaterializer.create(system);

        // product data sync runnable graph
        Source.tick(INITIAL_DELAY, INTERVAL, TICK)
                .mapConcat(t -> eventStoreRepository.findAll())
                .via(messageTranslationFlow.toEvents())
                .via(rabbitmqFlow.publish())
                .runWith(Sink.ignore(), materializer);
    }
}
