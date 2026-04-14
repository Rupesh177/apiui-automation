package org.rupesh.app.core.processor;

import org.rupesh.app.core.failure.FailureContext;

public interface FailureProcessor {
    void process(FailureContext context);
}