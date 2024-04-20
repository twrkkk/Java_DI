import interfaces.AutoInjectable;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {
    private String pathToProperties;
    public Injector(String pathToProperties)
    {
        this.pathToProperties = pathToProperties;
    }

    public<T> T inject(T impl)
    {
        Properties properties = loadProperties(pathToProperties);

        for (Field field : impl.getClass().getDeclaredFields())
        {
            if(field.isAnnotationPresent(AutoInjectable.class))
            {
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
    public Properties loadProperties(String path)
    {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
;
}
