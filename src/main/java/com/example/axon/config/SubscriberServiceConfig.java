/*
 * Copyright (c) 2010-2016. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.axon.config;

import com.example.axon.domain.model.SubscriberService;
import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.NoCache;
import org.axonframework.eventsourcing.*;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriberServiceConfig {

    public static final int SNAPSHOT_THRESHOLD = 10;

    @Autowired
    private EventStore eventStore;

    //@Autowired
    //FIXME: add caching
    private Cache cache = NoCache.INSTANCE;

    @Bean
    public SpringAggregateSnapshotterFactoryBean snapshotterFactoryBean() {
        return new SpringAggregateSnapshotterFactoryBean();
    }

    @Bean
    public Snapshotter snapshotter() throws Exception {
        return snapshotterFactoryBean().getObject();
    }
    @Bean
    public EventSourcingRepository<SubscriberService> subscriberServiceRepository(Snapshotter snapshotter) {
        EventCountSnapshotTriggerDefinition snapshotTriggerDefinition = new EventCountSnapshotTriggerDefinition(
                snapshotter,
                SNAPSHOT_THRESHOLD);

        return new CachingEventSourcingRepository<SubscriberService>(
                new GenericAggregateFactory(SubscriberService.class),
                eventStore,
                cache,
                snapshotTriggerDefinition);
    }



}