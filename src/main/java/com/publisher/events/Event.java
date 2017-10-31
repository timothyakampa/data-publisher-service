package com.publisher.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Event {

    @JsonIgnore
    public String asJsonString() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
