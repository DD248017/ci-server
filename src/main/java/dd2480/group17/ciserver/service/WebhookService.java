package dd2480.group17.ciserver.service;

import com.fasterxml.jackson.databind.JsonNode;

public class WebhookService {

    private static final GitService gitService = new GitService();
    private static final CompileService compileService = new CompileService();
    private static final TestService testService = new TestService();
    private static final NotificationService notificationService = new NotificationService();
    private static final HistoryService historyService = new HistoryService();

    // TODO gets the payload in json and determind if this is a github event
    // Extract important information
    public void processWebhookEvent(JsonNode jsonPayload) {
        // Parse json

        // handlePushEvent

    }

    // TODO if it is a github event send neccessary information to githubservices

    private void handlePushEvent(JsonNode jsonPayload) {

        // gitService.fetchRepository("https://github.com/DD248017/ci-server", "test");

        // Notify error

        // compileService.CompileCode
        // testService.TestCode

        // Notify result

    }
}
