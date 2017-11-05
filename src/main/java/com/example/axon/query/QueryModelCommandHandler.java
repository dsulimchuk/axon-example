package com.example.axon.query;

import com.example.axon.domain.events.AbstractSubscriberServiceEvent;
import com.example.axon.domain.model.SubscriberService;
import com.example.axon.query.model.SubscriberServiceQueryModel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QueryModelCommandHandler {

    private final ServiceRepository repository;
    private final EventSourcingRepository<SubscriberService> aggregateRepository;

    public QueryModelCommandHandler(ServiceRepository repository,
                                    EventSourcingRepository<SubscriberService> aggregateRepository) {
        this.repository = repository;
        this.aggregateRepository = aggregateRepository;
    }

    @EventHandler
    public void on(AbstractSubscriberServiceEvent changeCommand) {
        log.info("on {}", changeCommand);
        aggregateRepository.load("555")
                .invoke(aggregate -> repository.save(makeCopyFromAggregate(aggregate)));
    }

    private SubscriberServiceQueryModel makeCopyFromAggregate(SubscriberService aggregate ) {
        SubscriberServiceQueryModel result = new SubscriberServiceQueryModel();
        result.setId(aggregate.getId());
        result.setServiceId(aggregate.getServiceId());
        result.setSubscriberId(aggregate.getSubscriberId());
        result.setStatus(aggregate.getStatus());
        return result;
    }
}
