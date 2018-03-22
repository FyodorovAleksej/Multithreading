package by.fyodorov.multithreading.reader;

import by.fyodorov.multithreading.exception.MultiThreadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.AbstractMap;
import java.util.Map.Entry;

/**
 * class of parser for parsing lines of products
 */
public class ProductParser {
    private static final Logger LOGGER = LogManager.getLogger(ProductParser.class);
    static final String SPLITTER = "-";

    /**
     * parse line wit product from file to String (name) and Integer (count)
     * @param line - line from file
     * @return - Entry{String, Integer} - name and count of product in current line
     * @throws MultiThreadException - if line wasn't correct
     */
    public Entry<String, Integer> parse(String line) throws MultiThreadException {
        FileValidator validator = new FileValidator();
        if (!validator.validateLine(line)) {
            throw new MultiThreadException("Invalid String = \"" + line + "\"");
        }
        String[] elements = line.split(SPLITTER);
        String key = elements[0];
        Integer value = Integer.valueOf(elements[1]);
        LOGGER.info("getting key = \"" + key + "\"; value = \"" + value + "\"");
        return new AbstractMap.SimpleEntry<>(key, value);
    }
}
