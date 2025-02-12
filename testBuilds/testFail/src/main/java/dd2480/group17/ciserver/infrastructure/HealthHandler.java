package dd2480.group17.ciserver.infrastructure;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * Handler for the health check endpoint.
 * This class handles HTTP requests and responds with a JSON message
 * indicating the server's health status.
 */
public class HealthHandler extends AbstractHandler {

    /**
     * Handles incoming HTTP requests to the health check endpoint.
     * 
     * @param target      the target of the request, which is the request URI
     * @param baseRequest the Jetty request object
     * @param request     the HttpServletRequest object
     * @param response    the HttpServletResponse object
     * @throws IOException if an input or output error occurs while handling the
     *                     request
     */
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        String healthResponse = "{ \"status\": \"OK\" }";
        response.getWriter().println(healthResponse);
    }
}
