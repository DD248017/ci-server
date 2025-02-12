package dd2480.group17.ciserver.infrastructure;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The HistoryHandlerTest class contains unit tests for the
 * HistoryHandler class.
 * 
 * This test class verifies that the HistoryHandler correctly processes
 * HTTP GET requests sent to the "/history" endpoint by checking
 */
public class HistoryHandlerTest {

    /**
     * The Jetty server instance used for testing the HistoryHandler.
     */
    private static Server server;

    /**
     * The port number on which the test server is started.
     */
    private static final int PORT = 8017;

    /**
     * Sets up the Jetty server before all tests are run.
     * A new Jetty server is started on the specified port and a HistoryHandler is
     * mapped to the "/history" context.
     *
     * throws an exception if an error occurs while starting the server.
     */
    @BeforeAll
    static void setUp() throws Exception {
        server = new Server(PORT);
        ContextHandler context = new ContextHandler("/history");
        context.setHandler(new HistoryHandler());
        server.setHandler(context);
        server.start();
    }

    /**
     * Stops the Jetty server after all tests have been executed.
     *
     * throws an exception if an error occurs while stopping the server.
     */
    @AfterAll
    static void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Tests that the HistoryHandler
     * This test sends an HTTP GET request to the "/history" endpoint and asserts
     * that: The HTTP response code is 200 (OK).
     *
     * throws an exception if an error occurs while opening the HTTP connection or
     * reading the response.
     */
    @Test
    void testHistoryHandler() throws IOException {
        URL url = new URL("http://localhost:" + PORT + "/history");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, responseCode);
    }
}
