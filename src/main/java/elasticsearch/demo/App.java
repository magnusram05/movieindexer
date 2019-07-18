package elasticsearch.demo;

import elasticsearch.demo.cache.ElasticCacheAdaptor;
import elasticsearch.demo.cache.ICacheAdaptor;
import elasticsearch.demo.model.Movie;
import elasticsearch.demo.processor.FileProcessor;
import elasticsearch.demo.processor.IParser;
import elasticsearch.demo.processor.IProcessor;
import elasticsearch.demo.service.MovieCacheService;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        RestHighLevelClient client = getRestClient();
        MovieCacheService cacheService = new MovieCacheService(getMovieCacheAdaptor(client), getFileProcessor()
                , "C:\\dev\\javaweekly\\elasticsearch-demo\\data.tsv");
        cacheService.loadData();
        try {
            client.close();
        } catch (IOException ex) {
        }
    }

    private static RestHighLevelClient getRestClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
        return client;
    }

    private static ICacheAdaptor<Map<String, String>, Boolean> getMovieCacheAdaptor(RestHighLevelClient client) {
        ICacheAdaptor<Map<String, String>, Boolean> iCacheAdaptor = new ElasticCacheAdaptor(client);
        iCacheAdaptor.setCacheName("movie");
        return iCacheAdaptor;
    }

    private static IProcessor<File, List<Movie>> getFileProcessor() {
        IParser<String, Movie> parser = getMovieParser();
        IProcessor<File, List<Movie>> fileListIProcessor = new FileProcessor<>(parser);
        return fileListIProcessor;
    }

    private static IParser<String, Movie> getMovieParser() {
        return (s) -> {
            String[] movie = s.split("\t");
            Movie.Builder builder = new Movie.Builder();
            if (movie != null && movie.length == 9) {
                builder.setTconst(movie[0]);
                builder.setTitleType(movie[1]);
                builder.setPrimaryTitle(movie[2]);
                builder.setOriginalTitle(movie[3]);
                builder.setAdult(Boolean.valueOf(movie[4]));
                builder.setStartYear(movie[5]);
                builder.setEndYear(movie[6]);
                builder.setRuntimeMinutes(movie[7]);
                builder.setGenres(Arrays.asList(movie[8].split(",")));
            }
            return builder.build();
        };
    }
}
