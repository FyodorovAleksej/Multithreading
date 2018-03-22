package by.fyodorov.multithreading.util;

import java.util.HashMap;
import java.util.Map;

/**
 * util class for decorate Map for output
 */
public class MapDecorator {
    private static final String SPLITTER = "\t- ";
    private static final String END_LINE = "\n";

    /**
     * method for transform hash map into string for output
     * @param map - hash map for transforming
     * @return result String of transforming
     */
    public String hashMapToString(HashMap<String, Integer> map) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            builder.append(entry.getKey()).append(SPLITTER).append(entry.getValue()).append(END_LINE);
        }
        return builder.toString();
    }
}
