package com.publisher.models;


import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "PRODUCT_EVENT_QUEUE")
public class ProductEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_SEQ")
    @SequenceGenerator(name = "PRODUCT_SEQ")
    Long id;

    @Column(name = "PRODUCT_ID")
    String productId;

    @Column(name = "EVENT_TYPE")
    String eventType;

}
