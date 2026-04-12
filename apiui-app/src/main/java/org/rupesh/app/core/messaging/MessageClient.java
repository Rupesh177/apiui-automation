package org.rupesh.app.core.messaging;

import java.util.List;

public interface MessageClient {
    List<String> consume(String topic);
}
