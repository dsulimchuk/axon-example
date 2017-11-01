package com.example.axon;

import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.jpa.JpaEventStorageEngine;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.*;

@SpringBootApplication
public class AxonApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AxonApplication.class, args);
        CommandGateway gateway = context.getBean(CommandGateway.class);

        /*List<CompletableFuture<Object>> results = IntStream.range(1, 1000)
                .mapToObj(String::valueOf)
                .map(val -> gateway.send(val))
                .collect(Collectors.toList());

        results.forEach(System.out::println);
*/
        gateway.send(new ActivateCommand(123, 555));
        gateway.send(new DeactivateCommand(123, 555));
        gateway.send(new BlockCommand(123, 555));
        gateway.send(new ActivateCommand(123, 555));

        System.out.println("vse");


    }


    @Bean
    public EventStorageEngine eventStorageEngine(EntityManagerProvider entityManagerProvider,
                                                 SpringTransactionManager transactionManager) {
        return new JpaEventStorageEngine(entityManagerProvider, transactionManager);
    }

    @Bean
    public SpringTransactionManager springTransactionManager(PlatformTransactionManager platformTransactionManager) {
        return new SpringTransactionManager(platformTransactionManager);
    }

    @Bean
    public EntityManagerProvider entityManagerProvider() {
        return new ContainerManagedEntityManagerProvider();
    }

    @Component
    class HHH {
        private final Logger log = LoggerFactory.getLogger(HHH.class);

        @EventHandler
        public void onMessage(String msg) {
            log.warn("event received: " + msg);
        }

        @CommandHandler
        public String onCommand(String msg) {
            String result = "command received: " + msg;
            log.warn(result);
            return result;
        }
    }
    /*@Bean
    public CommandHandler handler() {
        return new CommandHandler() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return Bean.class;
            }

            @Override
            public String commandName() {
                return "sobaka";
            }

            @Override
            public String routingKey() {
                return null;
            }

            @Override
            public Class<?> payloadType() {
                return null;
            }
        };
    }*/
}

@Entity
@Data
class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(updatable = false)
    private Long subscriberId;

    @Column(updatable = false)
    private Long serviceId;

    @Column(updatable = false)
    private String name;

    private ServiceStatus status;

    public Service(long subscriberId, long serviceId, String name) {
        this.subscriberId = subscriberId;
        this.serviceId = serviceId;
        this.name = name;
    }

    //JPA§
    protected Service() {
    }
}

enum ServiceStatus {
    ACTIVE, BLOCKED, INACTIVE
}