package hu.sticky.config;

import hu.sticky.App;

import java.io.IOException;
import java.util.Properties;

public class ConfigHelper {
    private static Properties properties = new Properties();

    static {
        try{
            properties.load(App.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getAllProperties() {
        return properties;
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
