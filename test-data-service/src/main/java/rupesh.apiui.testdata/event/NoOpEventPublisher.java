package rupesh.apiui.testdata.event;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class NoOpEventPublisher implements EventPublisher {

    @Override
    public void publishUserCreated(String userId) {
        // do nothing
    }
}
