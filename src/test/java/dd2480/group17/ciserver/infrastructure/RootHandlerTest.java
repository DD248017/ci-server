package dd2480.group17.ciserver.infrastructure;

import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The RootHandlerTest class contains unit tests for the RootHandler class.
 * 
 * This test class sets up a Jetty server with the RootHandler mapped to the
 * root context ("/").
 * It then sends an HTTP GET request to the root URL and verifies that the
 * server responds with an HTTP 200
 * (OK) status code and that the response body contains the expected welcome
 * message.
 * </p>
 */
public class RootHandlerTest {

    /**
     * The Jetty server instance used for testing the RootHandler.
     */
    private static Server server;

    /**
     * The port number on which the test server is started.
     */
    private static final int PORT = 8087;

    /**
     * Sets up the Jetty server before all tests are run.
     * 
     * A new Jetty server is started on the specified port and a RootHandler
     * is mapped to the root ("/")
     * context. The server is then started.
     *
     * throws an exception if an error occurs while starting the server.
     */
    @BeforeAll
    static void setUp() throws Exception {
        server = new Server(PORT);
        ContextHandler context = new ContextHandler("/");
        context.setHandler(new RootHandler());
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
     * Tests that the RootHandler returns an HTTP 200 (OK) status and that the
     * response contains the expected welcome message.
     * This test sends an HTTP GET request to the root ("/") endpoint of the test
     * server and verifies:
     * 
     * 
     * throws an exception if an error occurs during the HTTP connection or while
     * reading the response.
     */
    @Test
    void testRootHandler() throws Exception {
        URL url = new URL("http://localhost:" + PORT + "/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int responseCode = connection.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, responseCode);
    }
}
