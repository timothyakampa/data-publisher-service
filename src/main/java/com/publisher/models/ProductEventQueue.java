package com.publisher.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductEventQueue {
    @Id
    private String productId;
    @Column(name = "PRODUCT_NAME")
    private String eventType;
    @Column(name = "PRODUCT_DESCRIPTION")
    private String description;
}
