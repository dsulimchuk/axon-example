package com.example.axon.domain.model;

import com.example.axon.domain.commands.ActivateSubscriberServiceCommand;
import com.example.axon.domain.commands.CreateNewSubscriberServiceCommand;
import com.example.axon.domain.commands.DeactivateSubscriberServiceCommand;
import com.example.axon.domain.events.NewSubscriberServiceCreatedEvent;
import com.example.axon.domain.events.SubscriberServiceActivatedEvent;
import com.example.axon.domain.events.SubscriberServiceDeactivatedEvent;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@Data
@Slf4j
public class SubscriberService {
    @AggregateIdentifier
    private Long id;

    private Long subscriberId;
    private Long serviceId;
    private ServiceStatus status;

    //JPA
    protected SubscriberService() {
    }

    //region CommandHandlers
    @CommandHandler
    public SubscriberService(CreateNewSubscriberServiceCommand command) {
        log.debug("on {}", command);
        apply(new NewSubscriberServiceCreatedEvent(command.getId(), command.getSubscriberId(), command.getServiceId()));
    }

    @CommandHandler
    public void on(ActivateSubscriberServiceCommand command) {
        log.debug("on {}", command);
        apply(new SubscriberServiceActivatedEvent(command.getId()));
    }

    @CommandHandler
    public void on(DeactivateSubscriberServiceCommand command) {
        log.debug("on {}", command);
        apply(new SubscriberServiceDeactivatedEvent(command.getId()));
    }
    //endregion

    //region Hadlers
    @EventSourcingHandler
    private void on(NewSubscriberServiceCreatedEvent event) {
        log.debug("event received {}", event);
        this.id = event.getId();
        this.serviceId = event.getServiceId();
        this.subscriberId = event.getSubscriberId();
    }

    @EventSourcingHandler
    private void on(SubscriberServiceActivatedEvent event) {
        log.debug("event received {}", event);
        this.status = ServiceStatus.ACTIVE;
    }

    @EventSourcingHandler
    private void on(SubscriberServiceDeactivatedEvent ee) {
        log.debug("onActivateCommand {}", ee);
        this.status = ServiceStatus.INACTIVE;
    }
    //endregion

}

