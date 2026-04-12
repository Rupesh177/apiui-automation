@Component
public class KafkaEventPublisher implements EventPublisher {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publishUserCreated(String userId) {
        kafkaTemplate.send("user-created", userId);
    }
}