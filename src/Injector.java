import interfaces.AutoInjectable;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * The Injector class is responsible for dependency injection based on annotations.
 */
public class Injector {
    /**
     * Path to the properties file containing interface-implementation mappings.
     */
    private String pathToProperties;

    /**
     * Constructs an Injector object with the specified path to properties file.
     *
     * @param pathToProperties the path to the properties file
     */
    public Injector(String pathToProperties) {
        this.pathToProperties = pathToProperties;
    }

    /**
     * Injects dependencies into the provided implementation object based on annotations.
     *
     * @param <T>  the type of the implementation
     * @param impl the implementation object to inject dependencies into
     * @return the implementation object with dependencies injected
     */
    public <T> T inject(T impl) {
        Properties properties = loadProperties(pathToProperties);

        for (Field field : impl.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                Class<?> fieldType = field.getType();
                String interfaceName = properties.getProperty(fieldType.getName());

                try {
                    Object implementation = Class.forName(interfaceName).newInstance();
                    field.setAccessible(true);
                    field.set(impl, implementation);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return impl;
    }

    /**
     * Loads properties from the specified properties file.
     *
     * @param path the path to the properties file
     * @return properties loaded from the file
     */
    public Properties loadProperties(String path) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}

