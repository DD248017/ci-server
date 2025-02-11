package dd2480.group17.ciserver.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NotificationServiceTest {

    // Test that the github repo api is reachable
    @Test
    void testGitHubApiIsAvailable() throws Exception {

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.github.com/repos/DD248017/ci-server"))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Assert that the response status code is 200 (OK)
        assertEquals(200, response.statusCode(), "GitHub API should be available.");
    }

    @Test
    void testNotifySuccessSetsCommitStatus() throws Exception {
        NotificationService notificationService = new NotificationService();
        String commitHash = "bf70555c99f3af9bf5e372d4d5e0adefbdb85de2";

        // Assert that the response status code is 201 (has led to the creation of a
        // resource)
        assertEquals(201, notificationService.notifySuccess(commitHash), "GitHub should accept the success status.");
    }

    @Test
    void testNotifyFailureSetsCommitStatus() throws Exception {
        NotificationService notificationService = new NotificationService();
        String commitHash = "25590ef5a851873d1544cc60e58c36c84b5ec894";

        // Assert that the response status code is 201 (has led to the creation of a
        // resource)
        assertEquals(201, notificationService.notifyFailure(commitHash), "GitHub should accept the failure status.");
    }

    @Test
    void testNotifyWithInvalidCommitHash() {
        NotificationService notificationService = new NotificationService();
        String invalidCommitHash = "invalid123";

        int responseCode = notificationService.notifySuccess(invalidCommitHash);
        assertNotEquals(201, responseCode, "Invalid commit hash should not return 201.");
    }
}
