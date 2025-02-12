package dd2480.group17.ciserver.infrastructure;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dd2480.group17.ciserver.infrastructure.HealthHandler;

/**
 * The HealthHandlerTest class contains unit tests for the
 * HealthHandler class.
 * 
 * This test class sets up a Jetty server on a specified port and maps the
 * HealthHandler to the "/health" endpoint. It then sends an HTTP GET
 * request to the endpoint and verifies that the server responds with an HTTP
 * (OK) status code.
 * </p>
 */
public class HealthHandlerTest {

    /**
     * The Jetty server used for testing the HealthHandler.
     */
    private static Server server;

    /**
     * The port number on which the test server is started.
     */
    private static final int PORT = 8017;

    /**
     * Sets up the test environment before any test methods are executed.
     * This method initializes a new Jetty server on the specified port, creates a
     * context with the path "/health", assigns a new instance of
     * HealthHandler as
     * the handler for that context, and starts the server.
     * </p>
     *
     * @throws Exception if an error occurs during server initialization or startup.
     */
    @BeforeAll
    static void setUp() throws Exception {
        server = new Server(PORT);
        ContextHandler context = new ContextHandler("/health");
        context.setHandler(new HealthHandler());
        server.setHandler(context);
        server.start();
    }

    /**
     * Cleans up the test environment after all test methods have been executed.
     * This method stops the Jetty server, ensuring that any resources used during
     * the tests are released.
     *
     * throws exception if an error occurs while stopping the server.
     */
    @AfterAll
    static void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Tests the HealthHandler by sending an HTTP GET request to the "/health"
     * endpoint.
     * This test opens an HTTP connection to the HealthHandler endpoint, sends a GET
     * request
     *
     * throws an exception if an error occurs while creating the connection or
     * reading the response.
     */
    @Test
    void testHealthHandler() throws IOException {
        URL url = new URL("http://localhost:" + PORT + "/health");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, responseCode);
    }
}
