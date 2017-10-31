package com.publisher.events;

import com.publisher.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductEvent extends Event {
    private String eventType;
    private Product product;
}
