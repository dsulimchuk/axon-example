package com.example.axon.query.model;

import com.example.axon.domain.model.ServiceStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@Slf4j
public class SubscriberServiceQueryModel {
    @Id
    private Long id;

    @Column(updatable = false)
    private Long subscriberId;

    @Column(updatable = false)
    private Long serviceId;

    private ServiceStatus status;

    //JPA
    public SubscriberServiceQueryModel() {
    }
}

