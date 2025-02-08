package dd2480.group17.ciserver.utils;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {

    @Test
    void testLoadServerPort() {
        ConfigLoader configLoader = new ConfigLoader();
        int expectedPort = 8017;
        assertEquals(expectedPort, configLoader.getPort(), "Port number loading failed");
    }

    @Test
    void testInvalidPropertyReturnsDefault() {
        ConfigLoader configLoader = new ConfigLoader();
        assertNull(configLoader.getProperty("invalid.key"), "Non-existent key should return null");
    }
}

