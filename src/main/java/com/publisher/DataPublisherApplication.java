package com.publisher;

import com.publisher.streams.PublishingGraph;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DataPublisherApplication implements CommandLineRunner {

    @Autowired
    private PublishingGraph publishingGraph;

    public static void main(String[] args) {
        SpringApplication.run(DataPublisherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("\n....................................\n Running rabbitmq publisher");
        publishingGraph.run();
    }
}
