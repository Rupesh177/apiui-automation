package org.rupesh.app.core.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.rupesh.app.utils.Config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.*;
import org.rupesh.app.exceptionNretry.FrameworkException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class KafkaClient implements MessageClient {

    private static final Logger log =
            LoggerFactory.getLogger(KafkaClient.class);

    @Override
    public List<String> consume(String topic) {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.getKafkaUrl());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group-" + UUID.randomUUID());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");

        List<String> messages = new ArrayList<>();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {

            consumer.subscribe(Collections.singletonList(topic));

            Duration timeout = Duration.ofSeconds(
                    Config.getInt("kafka.poll.timeout", 10)
            );

            ConsumerRecords<String, String> records = consumer.poll(timeout);

            for (ConsumerRecord<String, String> record : records) {
                messages.add(record.value());
            }

            log.info("Kafka consume completed. topic={} count={}", topic, messages.size());

            return messages;

        } catch (Exception e) {
            log.error("Kafka consume failed. topic={}", topic, e);
            throw new FrameworkException("Kafka consume failed", e);
        }
    }
}