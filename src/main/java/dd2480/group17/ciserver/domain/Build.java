package dd2480.group17.ciserver.domain;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import dd2480.group17.ciserver.infrastructure.WebhookListener;
import dd2480.group17.ciserver.utils.ConfigLoader;

public class Build extends AbstractHandler {

    private final WebhookListener webhookListener;

    public Build() {
        this.webhookListener = new WebhookListener();
    }

    /**
     * This method is called when a POST request is sent to the server.
     * It reads the payload from the request, runs the tests and sends a response.
     *
     * @param target      The target of the request
     * @param baseRequest The original unwrapped request object
     * @param request     The request either as the {@link HttpServletRequest}
     *                    or as a wrapper of that request
     * @param response    The response as the {@link HttpServletResponse}
     *                    or as a wrapper of that request
     * @throws IOException      If an I/O error occurred while handling the request
     * @throws ServletException If an error occurred while handling the request
     */
    @Override
    public void handle(String target,
            Request baseRequest,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println("Received request at target: " + target);

        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        webhookListener.handleWebhookEvent(request);

        response.getWriter().println("Test job done");
    }

    /**
     * This method starts the server and listens for incoming requests.
     *
     * @throws Exception If an error occurred while starting the server
     */
    public void run() throws Exception {
        ConfigLoader configLoader = new ConfigLoader();
        int port = configLoader.getPort();

        Server server = new Server(port);

        server.setHandler(new Build());
        server.start();
        server.join();
    }
}
