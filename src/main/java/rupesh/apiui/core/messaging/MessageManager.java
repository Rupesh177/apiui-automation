package rupesh.apiui.core.messaging;

public class MessageManager {

    private static final MessageClient client = new KafkaClient();

    public static MessageClient get() {
        return client;
    }
}