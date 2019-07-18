package elasticsearch.demo.model;

public interface Cacheable<K> {
    K getKey();
}
