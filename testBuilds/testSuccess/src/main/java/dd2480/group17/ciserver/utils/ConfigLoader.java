package dd2480.group17.ciserver.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigLoader is used to load the application.properties and log4j.properties
 * configuration files.
 */
public class ConfigLoader {
    private static final Properties APPLICATION_PROPERTIES = new Properties();
    private static final Properties LOG4J_PROPERTIES = new Properties();

    static {
        String applicationPath = "dd2480/group17/ciserver/config/application.properties";
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(applicationPath)) {
            if (input == null) {
                throw new IOException("Configuration file application.properties not found!");
            }
            APPLICATION_PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + e.getMessage(), e);
        }

        String log4jPath = "dd2480/group17/ciserver/config/log4j.properties";
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(log4jPath)) {
            if (input == null) {
                throw new IOException("Configuration file log4j.properties not found!");
            }
            LOG4J_PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves the value of a specified property from a given Properties object.
     *
     * @param properties The properties object to retrieve the value from.
     * @param key        The key of the property.
     * @return The value of the property as a String, or null if the key does not
     *         exist.
     */
    public String getProperty(Properties properties, String key) {
        return properties.getProperty(key);
    }

    /**
     * Retrieves the integer value of a specified property from a given Properties
     * object.
     *
     * @param properties The properties object to retrieve the value from.
     * @param key        The key of the property.
     * @return The integer value of the property, or -1 if the key does not exist or
     *         cannot be parsed as an integer.
     */
    public int getIntProperty(Properties properties, String key) {
        String value = getProperty(properties, key);
        return value != null ? Integer.parseInt(value) : -1;
    }

    /**
     * Retrieves the port number from the configuration file.
     * The port number is calculated as the sum of "server.base" and "group.number"
     * properties.
     *
     * @return The computed port number.
     */
    public int getPort() {
        int serverBase = getIntProperty(APPLICATION_PROPERTIES, "server.base");
        int groupNumber = getIntProperty(APPLICATION_PROPERTIES, "group.number");
        return serverBase + groupNumber;
    }

    /**
     * Retrieves the log filename format for webhook events from the log4j
     * properties file.
     *
     * @return The log filename format for webhook events.
     */
    public String getLogFormatWebhook() {
        return getProperty(LOG4J_PROPERTIES, "log.format.webhook");
    }

    /**
     * Retrieves the log format for compile operations from the log4j properties
     * file.
     *
     * @return The log format for compile operations.
     */
    public String getLogFormatCompile() {
        return getProperty(LOG4J_PROPERTIES, "log.format.compile");
    }

    /**
     * Retrieves the log format for test operations from the log4j properties file.
     *
     * @return The log format for test operations.
     */
    public String getLogFormatTest() {
        return getProperty(LOG4J_PROPERTIES, "log.format.test");
    }
}
