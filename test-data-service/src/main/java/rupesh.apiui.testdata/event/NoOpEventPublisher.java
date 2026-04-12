@Component
@Primary
public class NoOpEventPublisher implements EventPublisher {

    @Override
    public void publishUserCreated(String userId) {
        // do nothing
    }
}