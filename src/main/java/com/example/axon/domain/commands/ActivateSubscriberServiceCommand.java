package com.example.axon.domain.commands;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class ActivateSubscriberServiceCommand {
    @TargetAggregateIdentifier
    private final long id;
}
