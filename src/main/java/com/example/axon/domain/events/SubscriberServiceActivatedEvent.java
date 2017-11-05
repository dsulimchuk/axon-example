package com.example.axon.domain.events;

import lombok.Value;

@Value
public class SubscriberServiceActivatedEvent extends AbstractSubscriberServiceEvent {
    public SubscriberServiceActivatedEvent(long id) {
        super(id);
    }
}
