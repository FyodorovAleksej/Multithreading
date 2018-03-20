package by.fyodorov.multithreading.reader;

import by.fyodorov.multithreading.exception.MultiThreadException;
import by.fyodorov.multithreading.util.StorageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map.Entry;

public class StorageFileBuilder implements AbstractStorageBuilder {
    private static final Logger LOGGER = LogManager.getLogger(StorageFileBuilder.class);
    private StorageUtil result = null;

    public void build(String path) throws MultiThreadException {
        StorageReader reader = new StorageReader();
        String[] lines = reader.readLines(path);
        StorageParser parser = new StorageParser();

        int bound = Integer.valueOf(lines[0]);
        result = new StorageUtil(bound);

        LOGGER.info("creating new storage with max capacity = " + bound);

        for (int i = 1; i < lines.length; i++) {
            Entry<String, Integer> entry = parser.parse(lines[i]);
            if (entry != null) {
                result.addValue(entry.getKey(), entry.getValue());
            }
        }
    }

    public StorageUtil getResult() {
        return result;
    }
}
