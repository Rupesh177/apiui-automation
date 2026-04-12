package org.rupesh.test.data.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher implements EventPublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publishUserCreated(String userId) {
        kafkaTemplate.send("user-created", userId);
    }
}
