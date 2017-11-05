package com.example.axon.domain.events;

import lombok.Value;

@Value
public class NewSubscriberServiceCreatedEvent {
    private Long id;
    private Long subscriberId;
    private Long serviceId;
}
