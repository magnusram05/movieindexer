package elasticsearch.demo.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FileProcessor<S> implements IProcessor<File, List<S>> {

    private static Logger LOGGER = LogManager.getLogger(FileProcessor.class.getName());

    private IParser<String, S> processorStrategy;

    public FileProcessor(IParser<String, S> processorStrategy) {
        this.processorStrategy = processorStrategy;
    }

    @Override
    public void process(File input, Consumer<List<S>> consumer) {
        List<S> processedResult = new ArrayList<>();
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                count++;
                S parsed = processorStrategy.parse(line);
                if (count <= 1000) {
                    processedResult.add(parsed);
                } else {
                    consumer.accept(processedResult);
                    processedResult.clear();
                    LOGGER.info("Processed 1000...");
                    processedResult.add(parsed);
                    count = 1;
                }
            }
        } catch (FileNotFoundException ex) {
            LOGGER.error("Source file not found: ", ex);
        } catch (IOException ex) {
            LOGGER.error("IOException while processing the source file: ", ex);
        }
    }
}
