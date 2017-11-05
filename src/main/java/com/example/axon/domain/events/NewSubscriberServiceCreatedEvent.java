package com.example.axon.domain.events;

import lombok.Value;

@Value
public class NewSubscriberServiceCreatedEvent extends AbstractSubscriberServiceEvent {
    private Long subscriberId;
    private Long serviceId;

    public NewSubscriberServiceCreatedEvent(Long id, Long subscriberId, Long serviceId) {
        super(id);
        this.subscriberId = subscriberId;
        this.serviceId = serviceId;
    }
}
