package com.example.axon.domain.commands;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class DeactivateSubscriberServiceCommand {
    @TargetAggregateIdentifier
    private final long id;
}
