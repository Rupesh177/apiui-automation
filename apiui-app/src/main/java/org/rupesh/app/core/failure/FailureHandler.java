package org.rupesh.app.core.failure;

import org.rupesh.app.core.processor.FailureProcessor;

import java.util.List;

public class FailureHandler {

    private final List<FailureProcessor> processors;

    public FailureHandler(List<FailureProcessor> processors) {
        this.processors = processors != null ? processors : List.of();
    }

    public void handle(FailureContext context) {
        for (FailureProcessor processor : processors) {
            try {
                processor.process(context);
            } catch (Exception e) {
                // swallow to allow remaining processors to run
                // individual processors should log their own failures
            }
        }
    }
}