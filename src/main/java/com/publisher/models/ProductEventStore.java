package com.publisher.models;


import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity(name = "PRODUCT_EVENT_STORE")
public class ProductEventStore {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUST_SEQ")
    @SequenceGenerator(sequenceName = "customer_seq", allocationSize = 1, name = "CUST_SEQ")
    Long id;

    @Column(name = "PRODUCT_ID")
    String productId;

    @Column(name = "EVENT_TYPE")
    String eventType;
}
