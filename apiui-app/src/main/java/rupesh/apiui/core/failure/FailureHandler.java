package rupesh.apiui.core.failure;



import java.util.List;

public class FailureHandler {

    private final List<FailureProcessor> processors;

    public FailureHandler(List<FailureProcessor> processors) {
        this.processors = processors;
    }

    public void handle(FailureContext context) {
        for (FailureProcessor p : processors) {
            p.process(context);
        }
    }
}