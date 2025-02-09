package dd2480.group17.ciserver.infrastructure;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import dd2480.group17.ciserver.infrastructure.dto.WebhookDTO;
import dd2480.group17.ciserver.service.WebhookService;

public class WebhookListener extends AbstractHandler {
    private static final WebhookService webhookService = new WebhookService();

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            WebhookDTO webhookDTO = parseWebhookPayload(request);
            webhookService.processWebhookEvent(webhookDTO);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Failed to process webhook: " + e.getMessage());
        }
    }

    /**
     * Extracts and parses the JSON payload from an HttpServletRequest into a
     * WebhookDTO.
     *
     * @param request The HTTP request containing the webhook payload
     * @return WebhookDTO representation of the payload
     * @throws IOException If an error occurs while reading the request
     */
    private WebhookDTO parseWebhookPayload(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(requestBody.toString(), WebhookDTO.class);
    }
}
