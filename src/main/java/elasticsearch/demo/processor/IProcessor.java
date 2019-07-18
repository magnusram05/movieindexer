package elasticsearch.demo.processor;

import java.util.function.Consumer;

public interface IProcessor<I, O> {
    void process(I input, Consumer<O> consumer);
}
