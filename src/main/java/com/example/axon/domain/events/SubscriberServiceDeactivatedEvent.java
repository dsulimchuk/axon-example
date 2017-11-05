package com.example.axon.domain.events;

import lombok.Value;

@Value
public class SubscriberServiceDeactivatedEvent extends AbstractSubscriberServiceEvent {
    public SubscriberServiceDeactivatedEvent(long id) {
        super(id);
    }
}
