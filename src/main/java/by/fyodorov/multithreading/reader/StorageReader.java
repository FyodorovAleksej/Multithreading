package by.fyodorov.multithreading.reader;


import by.fyodorov.multithreading.exception.MultiThreadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StorageReader {
    private static final Logger LOGGER = LogManager.getLogger(StorageReader.class);

    public String[] readLines(String path) throws MultiThreadException {
        Stream<String> stream = null;
        try {
            stream = Files.lines(Paths.get(path));
            LOGGER.info("file \"" + path + "\" was opened");
            return stream.toArray(String[]::new);
        } catch (IOException e) {
            throw new MultiThreadException("can't read file", e);
        }
        finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
}
