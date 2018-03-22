package by.fyodorov.multithreading.port;

/**
 * class of manager for getting portBuilder by enum
 */
public class PortBuilderManager {

    /**
     * finding selected portBuilder by enum
     * @param builderEnum - selected enum
     * @param path - path of file for FileBuilder. Can be null for DefaultBuilder
     * @return - selected PortBuilder
     */
    public PortBuilder findBuilderByEnum(PortBuilderEnum builderEnum, String path) {
        switch (builderEnum) {
            case FILE: {
                return new PortFileBuilder(path);
            }
            case DEFAULT: {
                return new PortDefaultBuilder();
            }
            default: {
                return new PortDefaultBuilder();
            }
        }
    }
}
