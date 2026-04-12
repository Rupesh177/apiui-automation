package org.rupesh.app.core.messaging;

public class MessageManager {

    private static final MessageClient client = new KafkaClient();

    public static MessageClient get() {
        return client;
    }
}