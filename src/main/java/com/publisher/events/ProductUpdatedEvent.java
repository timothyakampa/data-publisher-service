package com.publisher.events;

import lombok.*;
import lombok.experimental.Wither;


@Getter
@Setter
@ToString
@Wither
@EqualsAndHashCode(callSuper = true)
public class ProductUpdatedEvent extends Event {
    private static final String PRODUCT_UPDATED_EVENT = "PRODUCT_UPDATED";

    private String productName;
    private String description;

    public ProductUpdatedEvent() {
        super(PRODUCT_UPDATED_EVENT);
    }

    public ProductUpdatedEvent(String productName, String description) {
        super(PRODUCT_UPDATED_EVENT);
        this.productName = productName;
        this.description = description;
    }
}
