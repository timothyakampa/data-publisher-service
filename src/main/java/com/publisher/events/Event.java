package com.publisher.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class Event {

    @Setter
    @Getter
    private String eventType;

    @JsonIgnore
    public String asJsonString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
