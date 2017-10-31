package com.publisher;

import com.publisher.streams.ProductDataPublisherGraph;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class DataPublisherApplication implements CommandLineRunner {

    @Autowired
    private ProductDataPublisherGraph productDataPublisherGraph;

    public static void main(String[] args) {
        SpringApplication.run(DataPublisherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("\n....................................\n Product data publisher graph");
        productDataPublisherGraph.run();
    }
}
