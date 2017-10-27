package com.publisher.streams;

import akka.Done;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.publisher.events.ProductUpdatedEvent;
import com.publisher.streams.flows.RabbitmqFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.CompletionStage;

@Slf4j
@Component
public class PublishingGraph {

    @Autowired
    private RabbitmqFlow rabbitmqFlow;

    public void run() {
        final ActorSystem system = ActorSystem.create("data-publisher");
        final Materializer materializer = ActorMaterializer.create(system);

        //Events from Database
        ProductUpdatedEvent event1 = new ProductUpdatedEvent().withProductName("productName1").withDescription("Description1");
        ProductUpdatedEvent event2 = new ProductUpdatedEvent().withProductName("productName2").withDescription("Description2");

        // Runnable graph
        CompletionStage<Done> done = Source.from(Arrays.asList(event1, event2))
                .via(rabbitmqFlow.publish())
                .runWith(Sink.ignore(), materializer);

        done.thenRun(system::terminate);
    }
}


/***

    Runnable graph for publishing messages to rabbit-mq
    ===================================================
    Source<ProductUpdatedEvent, NotUsed>
    Flow<ProductUpdatedEvent, Either[PublishError, Published], NotUsed> --> Sink or Retry Flow
    Sink<Stored, Future[Done]>

 ***/
