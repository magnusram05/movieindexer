package elasticsearch.demo.cache;

import java.util.List;

public interface ICacheAdaptor<I, O> {
    O create(I request);

    O bulkCreate(List<I> request);

    O read(I request);

    O update(I request);

    O delete(String key);

    void setCacheName(String cacheName);
}
