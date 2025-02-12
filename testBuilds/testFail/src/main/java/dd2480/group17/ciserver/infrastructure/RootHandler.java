package dd2480.group17.ciserver.infrastructure;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * A handler that processes HTTP requests to the root URL of the server.
 * This handler responds with a simple HTML message.
 */
public class RootHandler extends AbstractHandler {

    /**
     * Handles HTTP requests sent to the root of the server.
     *
     * @param target      The target URL of the request.
     * @param baseRequest The base Jetty request object.
     * @param request     The HTTP request object.
     * @param response    The HTTP response object.
     * @throws IOException If an I/O error occurs while processing the request.
     */
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Set the content type to HTML with UTF-8 encoding
        response.setContentType("text/html;charset=utf-8");

        // Set the HTTP status to 200 OK
        response.setStatus(HttpServletResponse.SC_OK);

        // Mark the request as handled
        baseRequest.setHandled(true);

        // Send a welcome message in HTML format
        response.getWriter().println("<h1>Welcome to the CI Server!</h1>");
    }
}
