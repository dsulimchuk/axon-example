package com.example.axon;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class ServiceCommandHandler {

    private final ServiceRepository repository;

    public ServiceCommandHandler(ServiceRepository repository) {
        this.repository = repository;
    }

    @CommandHandler
    @Transactional
    public void onActivateCommand(ActivateCommand command) {
        log.debug("onActivateCommand {}", command);
        Service service = getServiceForCommand(command);
        service.setStatus(ServiceStatus.ACTIVE);
    }

    @CommandHandler
    @Transactional
    public void onDeactivateCommand(DeactivateCommand command) {
        log.debug("onActivateCommand {}", command);
        Service service = getServiceForCommand(command);
        service.setStatus(ServiceStatus.INACTIVE);
    }

    @CommandHandler
    @Transactional
    public void onBlockCommand(BlockCommand command) {
        log.debug("onActivateCommand {}", command);
        Service service = getServiceForCommand(command);
        service.setStatus(ServiceStatus.BLOCKED);
    }

    private Service getServiceForCommand(AbstractServiceCommand command) {
        Service service = repository.findBySubscriberIdAndServiceId(command.getSubscriberId(), command.getServiceId());
        if (service == null) {
            Service srv = new Service(command.getSubscriberId(),
                    command.getServiceId(),
                    "test");

            service = repository.save(srv);
        }
        return service;
    }
}

@Value
class ActivateCommand extends AbstractServiceCommand {
    public ActivateCommand(long subscriberId, long serviceId) {
        super(subscriberId, serviceId);
    }
}

@Value
class DeactivateCommand extends AbstractServiceCommand {
    public DeactivateCommand(long subscriberId, long serviceId) {
        super(subscriberId, serviceId);
    }
}

@Value
class BlockCommand extends AbstractServiceCommand {
    public BlockCommand(long subscriberId, long serviceId) {
        super(subscriberId, serviceId);
    }
}

@EqualsAndHashCode
@ToString
abstract class AbstractServiceCommand {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private long subscriberId;

    @Getter
    @Setter(AccessLevel.PRIVATE)

    private long serviceId;

    public AbstractServiceCommand(long subscriberId, long serviceId) {
        this.subscriberId = subscriberId;
        this.serviceId = serviceId;
    }
}