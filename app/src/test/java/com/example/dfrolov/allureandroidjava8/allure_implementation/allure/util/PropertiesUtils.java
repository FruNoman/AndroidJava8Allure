package com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util;



import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * The collection of properties utils methods.
 */
public final class PropertiesUtils {



    private static final String ALLURE_PROPERTIES_FILE = "allure.properties";

    private PropertiesUtils() {
    }

    public static Properties loadAllureProperties() {
        final Properties properties = new Properties();
        if (Objects.nonNull(ClassLoader.getSystemResource(ALLURE_PROPERTIES_FILE))) {
            try (InputStream stream = ClassLoader.getSystemResourceAsStream(ALLURE_PROPERTIES_FILE)) {
                properties.load(stream);
            } catch (IOException e) {

            }
        }
        properties.putAll(System.getProperties());
        return properties;
    }

}
