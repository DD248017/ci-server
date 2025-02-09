package dd2480.group17.ciserver.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigLoader is used to load the application.properties configuration file
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
     * Get the property value from the configuration file
     *
     * @param properties the properties object
     * @param key        the key of the property
     * @return the value of the property
     */
    public String getProperty(Properties properties, String key) {
        return properties.getProperty(key);
    }

    /**
     * Get the integer property value from the configuration file
     *
     * @param properties the properties object
     * @param key        the key of the property
     * @return the integer value of the property
     */
    public int getIntProperty(Properties properties, String key) {
        String value = getProperty(properties, key);
        return value != null ? Integer.parseInt(value) : -1;
    }

    /**
     * Get the port number from the configuration file
     *
     * @return port number
     */
    public int getPort() {
        int serverBase = getIntProperty(APPLICATION_PROPERTIES, "server.base");
        int groupNumber = getIntProperty(APPLICATION_PROPERTIES, "group.number");
        int port = serverBase + groupNumber;
        return port;
    }

    /**
     * Get the log filename format from the configuration file
     *
     * @return log filename format
     */
    public String getLogFormatWebhook() {
        return getProperty(LOG4J_PROPERTIES, "log.format.webhook");
    }

    /**
     * Get the log format for compile from the configuration file
     *
     * @return log format for compile
     */
    public String getLogFormatCompile() {
        return getProperty(LOG4J_PROPERTIES, "log.format.compile");
    }

    /**
     * Get the log format for test from the configuration file
     *
     * @return log format for test
     */
    public String getLogFormatTest() {
        return getProperty(LOG4J_PROPERTIES, "log.format.test");
    }
}