package dd2480.group17.ciserver.infrastructure;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * The {@code StatusSender} class is responsible for sending the status of a commit 
 * to GitHub's commit status API. It indicates whether a build has succeeded or failed.
 * <p>
 * It sends a POST request to GitHub's commit status API with a success or failure message
 * based on the build outcome.
 * </p>
 */
public class StatusSender {
    
    /**
     * The URL for GitHub's commit status API endpoint.
     */
    private static final String GITHUB_API_URL = "https://api.github.com/repos/DD248017/ci-server/statuses/";

    /**
     * The GitHub token used for authentication.
     */
    private static final String GITHUB_TOKEN = System.getenv("GITHUB_TOKEN");

    /**
     * The HTTP client used to send the request to GitHub.
     */
    private final HttpClient httpClient;

    /**
     * Constructs a new {@code StatusSender} instance, initializing the HTTP client.
     */
    public StatusSender() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Sends the status of a commit to GitHub, indicating whether the build passed or failed.
     * <p>
     * A POST request is sent to GitHub's commit status API with the provided commit hash 
     * and the build result (success or failure).
     * </p>
     * 
     * @param commitHash The SHA hash of the commit to update the status for.
     * @param isSuccess {@code true} if the build succeeded, {@code false} if the build failed.
     */
    public void sendStatus(String commitHash, boolean isSuccess) {
        String buildState = isSuccess ? "success" : "failure";
        String description = isSuccess ? "Build passed" : "Build failed";

        // Construct the payload for the POST request
        String payload = "{"
                + "\"state\": \"" + buildState + "\","
                + "\"description\": \"" + description + "\","
                + "\"context\": \"CI Build\""
                + "}";

        try {
            // Create the URI for the commit status API
            URI statusUrl = URI.create(GITHUB_API_URL + commitHash);
            
            // Build the POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(statusUrl)
                    .header("Authorization", "token " + GITHUB_TOKEN)
                    .header("Accept", "application/vnd.github.v3+json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload, StandardCharsets.UTF_8))
                    .build();

            // Send the request and handle the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("GitHub API Response: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

