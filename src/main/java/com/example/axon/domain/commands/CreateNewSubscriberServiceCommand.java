package com.example.axon.domain.commands;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class CreateNewSubscriberServiceCommand {
    @TargetAggregateIdentifier
    private Long id;
    private Long subscriberId;
    private Long serviceId;
}