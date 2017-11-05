package com.example.axon.query;

import com.example.axon.domain.model.SubscriberService;
import com.example.axon.query.model.SubscriberServiceQueryModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ServiceRepository extends CrudRepository<SubscriberServiceQueryModel, Long> {
    SubscriberService findBySubscriberIdAndServiceId(long subscriberId, long serviceId);

    List<SubscriberService> findBySubscriberId(long subscriberId);
}
