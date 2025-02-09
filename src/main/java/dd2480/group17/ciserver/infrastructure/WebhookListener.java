package dd2480.group17.ciserver.infrastructure;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dd2480.group17.ciserver.service.WebhookService;

public class WebhookListener {
    private static final WebhookService webhookService = new WebhookService();
    // private final WebhookService webhookService;

    // TODO delegate this work to WebhookService

    public void handleWebhookEvent(HttpServletRequest request) throws IOException {

        JsonNode jsonPayload = parseWebhookPayload(request);

        // webhookService.processWebhookEvent(jsonPayload);
        webhookService.processWebhookEvent(jsonPayload);

    }

    /**
     * Extracts and parses the JSON payload from an HttpServletRequest.
     *
     * @param request The HTTP request containing the webhook payload
     * @return JsonNode representation of the payload
     * @throws IOException If an error occurs while reading the request
     */
    private JsonNode parseWebhookPayload(HttpServletRequest request) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(requestBody.toString());
    }

}
