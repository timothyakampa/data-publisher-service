package com.publisher.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity(name = "PRODUCTS")
public class Product {
    @Id
    @Column(name = "ID")
    String productId;

    @Column(name = "PRODUCT_NAME")
    String name;

    @Column(name = "PRODUCT_DESCRIPTION")
    String description;

    @Column(name = "CREATED_DATE")
    Date date;
}
