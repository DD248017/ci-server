package dd2480.group17.ciserver.service;

import com.fasterxml.jackson.databind.JsonNode;

public class WebhookService {

    private static final GitService gitService = new GitService();
    private static final CompileService compileService = new CompileService();
    private static final TestService testService = new TestService();
    private static final NotificationService notificationService = new NotificationService();
    private static final HistoryService historyService = new HistoryService();

    public void processWebhookEvent(JsonNode jsonPayload) {

        // Test this if you want to clone a repo
        /*
         * boolean cloneSuccess =
         * gitService.fetchRepository("https://github.com/DD248017/ci-server", "test",
         * "./ci-server-test");
         */

        if (isGitHubPushEvent(jsonPayload)) {
            handlePushEvent(jsonPayload);
        } else {
            System.out.println("Not a GitHub push event.");

        }

    }

    private boolean isGitHubPushEvent(JsonNode jsonPayload) {
        JsonNode refNode = jsonPayload.path("ref");
        return !refNode.isMissingNode() && refNode.asText().startsWith("refs/heads/");
    }

    // TODO fix right function parameters
    private void handlePushEvent(JsonNode jsonPayload) {

        String repoUrl = jsonPayload.path("repository").path("clone_url").asText();
        String branch = jsonPayload.path("ref").asText().replace("refs/heads/", "");

        try {
            boolean cloneSuccess = gitService.fetchRepository(repoUrl, branch, "./newRepo");
            if (cloneSuccess) {
                boolean compileSuccess = compileService.compileCode("somepath");
                boolean testSuccess = testService.executeTests("somepath");

                // Notify based on the result of compile and test
                if (compileSuccess && testSuccess) {
                    notificationService.notifySuccess("some hash");
                } else {
                    notificationService.notifyFailure("some hash");
                }

                // Log the build history
                historyService.logBuild(repoUrl, branch, "some hash", compileSuccess, testSuccess);
            } else {
                // Notify failure to clone repository
                notificationService.notifyFailure("Failed to clone repository.");
            }
        } catch (Exception e) {
            notificationService.notifyFailure("An error occurred during processing: " + e.getMessage());
        }
    }
}
