package dd2480.group17.ciserver.infrastructure;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * The {@code StatusSender} class is responsible for updating the status of a
 * commit
 * on GitHub based on whether a build has succeeded or failed.
 * <p>
 * It sends a POST request to GitHub's commit status API with the appropriate
 * success or failure message.
 * </p>
 */
public class StatusSender {
    private static final String GITHUB_API_URL = "https://api.github.com/repos/DD248017/ci-server/statuses/";
    private static final String GITHUB_TOKEN = System.getenv("GITHUB_TOKEN");

    private final HttpClient httpClient;

    /**
     * Constructs a new {@code StatusSender} instance with an initialized HTTP
     * client.
     */
    public StatusSender() {
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Sends the status of a commit to GitHub indicating whether the build passed
     * or failed.
     *
     * @param commitHash The SHA hash of the commit to update the status for.
     * @param isSuccess  {@code true} if the build succeeded, {@code false} if it
     *                   failed.
     */
    public void sendStatus(String commitHash, boolean isSuccess) {
        String buildState = isSuccess ? "success" : "failure";
        String description = isSuccess ? "Build passed" : "Build failed";

        String payload = "{"
                + "\"state\": \"" + buildState + "\","
                + "\"description\": \"" + description + "\","
                + "\"context\": \"CI Build\""
                + "}";

        try {
            URI statusUrl = URI.create(GITHUB_API_URL + commitHash);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(statusUrl)
                    .header("Authorization", "token " + GITHUB_TOKEN)
                    .header("Accept", "application/vnd.github.v3+json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("GitHub API Response: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
