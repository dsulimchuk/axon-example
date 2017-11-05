package com.example.axon;

import com.example.axon.domain.events.NewSubscriberServiceCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;

//@Component
@Slf4j
public class ServiceCommandHandler {

    //private final ServiceRepository repository;
   // private final EventBus eventBus;


    /*public ServiceCommandHandler(ServiceRepository repository, EventBus eventBus) {
      //  this.repository = repository;
        this.eventBus = eventBus;
    }*/

    @EventHandler
    public void onActivatedEvent(NewSubscriberServiceCreatedEvent ee) {
        System.out.println("aaa suka");
        log.info("onActivateCommand {}", ee);

    }

    /*@CommandHandler
    @Transactional
    public void onActivateCommand(ActivateCommand command) {
        log.debug("onActivateCommand {}", command);
        Service service = getServiceForCommand(command);
        service.setStatus(ServiceStatus.ACTIVE);
        eventBus.publish(GenericEventMessage.asEventMessage(new ActivatedEvent(service.getServiceId())));
        //AggregateLifecycle.apply();
    }
*/


}

