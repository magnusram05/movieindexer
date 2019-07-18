package elasticsearch.demo.service;

import elasticsearch.demo.cache.ICacheAdaptor;
import elasticsearch.demo.model.Movie;
import elasticsearch.demo.processor.IProcessor;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MovieCacheService {
    private final ICacheAdaptor<Map<String, String>, Boolean> adaptor;
    private final IProcessor<File, List<Movie>> fileMovieIProcessor;
    private final String sourceFileLocation;

    public MovieCacheService(ICacheAdaptor<Map<String, String>, Boolean> adaptor,
                             IProcessor<File, List<Movie>> fileMovieIProcessor,
                             String sourceLocation) {
        this.adaptor = adaptor;
        this.fileMovieIProcessor = fileMovieIProcessor;
        this.sourceFileLocation = sourceLocation;
    }

    public void loadData() {
        Consumer<List<Movie>> moviesConsumer = (movieList) -> {
            this.adaptor.bulkCreate(movieList.stream().map((s) -> s.getList())
                    .collect(Collectors.toList()));
        };
        fileMovieIProcessor.process(new File(this.sourceFileLocation), moviesConsumer);
    }
}
