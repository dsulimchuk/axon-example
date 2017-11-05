package com.example.axon.domain.events;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class AbstractSubscriberServiceEvent {
    private final long id;
}
