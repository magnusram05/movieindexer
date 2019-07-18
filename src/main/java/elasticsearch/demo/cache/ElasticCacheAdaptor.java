package elasticsearch.demo.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ElasticCacheAdaptor implements ICacheAdaptor<Map<String, String>, Boolean> {

    private static Logger LOGGER = LogManager.getLogger(ElasticCacheAdaptor.class.getName());

    private String index;

    public ElasticCacheAdaptor(RestHighLevelClient client) {
        this.client = client;
    }

    private RestHighLevelClient client;

    public void setCacheName(String index) {
        this.index = index;
    }

    @Override
    public Boolean create(Map<String, String> request) {
        IndexRequest indexRequest = new IndexRequest(this.index, "doc").source(request);
        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException ex) {
            LOGGER.error("Error creating the index", ex);
            return false;
        }
        return true;
    }

    @Override
    public Boolean bulkCreate(List<Map<String, String>> request) {
        BulkRequest bulkRequest = new BulkRequest();
        BulkResponse bulkResponse = null;
        request.forEach((s) -> {
            IndexRequest indexRequest = new IndexRequest(this.index, "doc").source(s);
            bulkRequest.add(indexRequest);
        });

        try {
            bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException ex) {
            LOGGER.error("Error in bulk creating the index", ex);
        }
        return bulkResponse != null && !bulkResponse.hasFailures();
    }

    @Override
    public Boolean read(Map<String, String> request) {
        return false;
    }

    @Override
    public Boolean update(Map<String, String> request) {
        return false;
    }

    @Override
    public Boolean delete(String request) {
        return false;
    }
}
