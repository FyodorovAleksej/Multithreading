package by.fyodorov.multithreading.reader;

/**
 * class of validator for validate lines from files for Port and for Storage
 */
public class FileValidator {

    public FileValidator() {

    }

    /**
     * validate lines for StorageBuilder
     * @param lines - array of lines from file for creating Storage
     * @return - true - lines was correct
     *           else - lines wasn't correct
     */
    public boolean validateForStorage(String[] lines) {
        if (lines.length < 1) {
            return false;
        }
        try {
            Integer.parseInt(lines[0]);
        }
        catch (NumberFormatException e) {
            return false;
        }
        for (int i = 1; i < lines.length; i++) {
            if (!validateLine(lines[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * validate lines for PortBuilder
     * @param lines - array of lines from file for creating Storage
     * @return - true - lines was correct
     *           else - lines wasn't correct
     */
    public boolean validateForPort(String[] lines) {
        if (lines.length < 2) {
            return false;
        }
        try {
            Integer.parseInt(lines[0]);
            Integer.parseInt(lines[1]);
        }
        catch (NumberFormatException e) {
            return false;
        }
        for (int i = 2; i < lines.length; i++) {
            if (!validateLine(lines[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * validate line with Entry{String, Integer}
     * @param line - line of file with product
     * @return - true - line was correct
     *           false - line wasn't correct
     */
    public boolean validateLine(String line) {
        String[] parts = line.split(ProductParser.SPLITTER);
        if (parts.length <= 1) {
            return false;
        }
        try {
            Integer.parseInt(parts[1]);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
