package org.rupesh.app.core.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.rupesh.app.utils.Config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KafkaClient implements MessageClient{

    @Override
    public List<String> consume(String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", Config.getKafkaUrl());
        props.put("group.id", "test-group");
        props.put("auto.offset.reset", "latest");

        KafkaConsumer<String, String> consumer =
                new KafkaConsumer<>(props);

        consumer.subscribe(List.of(topic));

        ConsumerRecords<String, String> records =
                consumer.poll(Duration.ofSeconds(10));

        List<String> messages = new ArrayList<>();

        for (ConsumerRecord<String, String> record : records) {
            messages.add(record.value());
        }

        consumer.close();

        return messages;
    }
}