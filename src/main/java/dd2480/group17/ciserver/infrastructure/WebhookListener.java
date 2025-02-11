package dd2480.group17.ciserver.infrastructure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dd2480.group17.ciserver.utils.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import dd2480.group17.ciserver.infrastructure.dto.WebhookDTO;
import dd2480.group17.ciserver.service.WebhookService;

/**
 * The {@code WebhookListener} class is responsible for handling incoming POST
 * requests
 * containing webhook payloads. It processes the webhook events by converting
 * the JSON payload
 * into a {@link WebhookDTO} and then passing it to the {@link WebhookService}
 * for further handling.
 */
public class WebhookListener extends AbstractHandler {

    /**
     * The {@link WebhookService} instance used to process webhook events.
     */
    private static final WebhookService webhookService = new WebhookService();

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = new Logger();

    /**
     * Handles the incoming HTTP requests. Only POST requests are accepted, and the
     * payload is processed as a {@link WebhookDTO}.
     * <p>
     * If the request method is not POST, it responds with a
     * {@code 405 Method Not Allowed} status.
     * If there is any error during the webhook processing, a
     * {@code 400 Bad Request} status is returned.
     * </p>
     *
     * @param target      The target URL of the request.
     * @param baseRequest The base Jetty request object.
     * @param request     The HTTP request object.
     * @param response    The HTTP response object.
     * @throws IOException If an I/O error occurs while reading the request or
     *                     sending the response.
     */
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            if ("POST".equalsIgnoreCase(request.getMethod())) {

                // Parse the webhook payload
                String payload = parseWebhookPayload(request);
                WebhookDTO webhookDTO = objectMapper.readValue(payload, WebhookDTO.class);

                // Write the payload to a file
                logger.saveJsonToFile("webhook", webhookDTO.headCommit().id(), payload);

                // Process the webhook event
                webhookService.processWebhookEvent(webhookDTO);
            } else {
                // Return 405 Method Not Allowed for non-POST requests
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                response.getWriter().write("Only POST method allowed");
                return;
            }

            // Mark the request as handled and respond with a success message
            baseRequest.setHandled(true);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("CI job done!");
        } catch (Exception e) {
            // Handle exceptions and respond with a 400 Bad Request
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Failed to process webhook: " + e.getMessage());
        }
    }

    /**
     * Extracts and parses the JSON payload from the {@link HttpServletRequest} into
     * a {@link WebhookDTO}.
     * <p>
     * The payload is read from the request body and mapped to a {@link WebhookDTO}
     * using Jackson's
     * {@link ObjectMapper}.
     * </p>
     *
     * @param request The HTTP request containing the webhook payload.
     * @return A {@link WebhookDTO} representation of the payload.
     * @throws IOException If an error occurs while reading the request or parsing
     *                     the JSON.
     */
    private String parseWebhookPayload(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }
        return requestBody.toString();
    }
}
