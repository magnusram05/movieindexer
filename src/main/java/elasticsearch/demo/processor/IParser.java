package elasticsearch.demo.processor;

public interface IParser<I, O> {
    O parse(I input);
}
