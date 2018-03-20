package by.fyodorov.multithreading.reader;

import by.fyodorov.multithreading.exception.MultiThreadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.AbstractMap;
import java.util.Map.Entry;

public class StorageParser {
    private static final Logger LOGGER = LogManager.getLogger(StorageParser.class);
    private static final String SPLITTER = "-";

    public Entry<String, Integer> parse(String line) throws MultiThreadException {
        String[] elements = line.split(SPLITTER);
        if (elements.length > 1) {
            String key = elements[0];
            Integer value = Integer.valueOf(elements[1]);
            LOGGER.info("getting key = \"" + key + "\"; value = \"" + value + "\"");
            return new AbstractMap.SimpleEntry<>(key, value);
        }
        throw new MultiThreadException("Invalid String = \"" + line + "\"");
    }
}
