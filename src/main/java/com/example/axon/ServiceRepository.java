package com.example.axon;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ServiceRepository extends CrudRepository<Service, Long>{

    Service findBySubscriberIdAndServiceId(long subscriberId, long serviceId);
}