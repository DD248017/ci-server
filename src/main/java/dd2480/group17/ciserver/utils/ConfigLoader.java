package dd2480.group17.ciserver.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigLoader is used to load the application.properties configuration file
 */
public class ConfigLoader {
    private static final Properties PROPERTIES = new Properties();

    static {
        String path = "dd2480/group17/ciserver/config/application.properties";
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                throw new IOException("Configuration file application.properties not found!");
            }
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + e.getMessage(), e);
        }
    }

    /**
     * Get the property value from the configuration file
     *
     * @param key the key of the property
     * @return the value of the property
     */
    public String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    /**
     * Get the integer property value from the configuration file
     *
     * @param key the key of the property
     * @return the integer value of the property
     */
    public int getIntProperty(String key) {
        String value = getProperty(key);
        return value != null ? Integer.parseInt(value) : -1;
    }
    /**
     * Get the port number from the configuration file
     *
     * @return port number
     */
    public int getPort() {
        return getServerBase() + getGroupNumber();
    }




    private int getServerBase() {
        return getIntProperty("server.base");
    }


    private int getGroupNumber() {
        return getIntProperty("group.number");
    }
}