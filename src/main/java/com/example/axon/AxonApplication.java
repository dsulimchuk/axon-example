package com.example.axon;

import com.example.axon.domain.commands.ActivateSubscriberServiceCommand;
import com.example.axon.domain.commands.CreateNewSubscriberServiceCommand;
import com.example.axon.domain.commands.DeactivateSubscriberServiceCommand;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

@SpringBootApplication
@Slf4j
public class AxonApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AxonApplication.class, args);
        init(context);
    }

    private static void init(ConfigurableApplicationContext context) {
        CommandGateway gateway = context.getBean(CommandGateway.class);

        gateway.send(new CreateNewSubscriberServiceCommand(555L, 123L, 555L));
        for (int i = 0; i < 1000; i++) {
            gateway.send(new ActivateSubscriberServiceCommand(555L));
            gateway.send(new DeactivateSubscriberServiceCommand(555L));
        }

        log.info("initialization finished");
    }


    @Bean
    public EventStorageEngine eventStorageEngine(EntityManagerProvider entityManagerProvider,
                                                 SpringTransactionManager transactionManager) {
        return new JpaEventStorageEngine(entityManagerProvider, transactionManager);
    }

    @Bean

    public SpringTransactionManager springTransactionManager(PlatformTransactionManager delegate) {
        return new SpringTransactionManager(new PlatformTransactionManager() {

            @Override
            public TransactionStatus getTransaction(TransactionDefinition transactionDefinition) throws TransactionException {
                log.info("!!get tx", transactionDefinition);
                return delegate.getTransaction(transactionDefinition);
            }

            @Override
            public void commit(TransactionStatus transactionStatus) throws TransactionException {
                log.info("!!commit {}", transactionStatus);
                delegate.commit(transactionStatus);
            }

            @Override
            public void rollback(TransactionStatus transactionStatus) throws TransactionException {
                log.info("!!rollback {}", transactionStatus);
                delegate.rollback(transactionStatus);
            }
        });
    }

    @Bean
    public EntityManagerProvider entityManagerProvider() {
        return new ContainerManagedEntityManagerProvider();
    }

    @Component
    @Slf4j
    static class HHH {
        @EventHandler
        public void onMessage(String msg) {
            log.info("event received: {}", msg);
        }

        @CommandHandler
        public void onCommand(String msg) {
            log.info("command received: {}", msg);
        }
    }

}



