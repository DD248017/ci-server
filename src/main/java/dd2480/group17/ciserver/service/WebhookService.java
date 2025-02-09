package dd2480.group17.ciserver.service;

import dd2480.group17.ciserver.infrastructure.dto.WebhookDTO;

public class WebhookService {

    private static final GitService gitService = new GitService();
    private static final CompileService compileService = new CompileService();
    private static final TestService testService = new TestService();
    private static final NotificationService notificationService = new NotificationService();
    private static final HistoryService historyService = new HistoryService();

    // TODO javadocs
    public void processWebhookEvent(WebhookDTO webhookDTO) {

        // Error handling here or check if it is pushevent
        handlePushEvent(webhookDTO);
    }

    // TODO get this work with other functions and javadocs
    private void handlePushEvent(WebhookDTO webhookDTO) {
        String repoUrl = webhookDTO.repository().htmlUrl();
        String branch = webhookDTO.branch();
        String commitHash = webhookDTO.after();

        try {
            boolean cloneSuccess = gitService.fetchRepository(repoUrl, branch, "./newRepo");
            if (cloneSuccess) {
                boolean compileSuccess = compileService.compileCode("./newRepo");
                boolean testSuccess = testService.executeTests("./newRepo");

                // Notify based on the result of compile and test
                if (compileSuccess && testSuccess) {
                    notificationService.notifySuccess(commitHash);
                } else {
                    notificationService.notifyFailure(commitHash);
                }

                // Log the build history
                historyService.logBuild(repoUrl, branch, commitHash, compileSuccess, testSuccess);
            } else {
                // Notify failure to clone repository
                notificationService.notifyFailure("Failed to clone repository.");
            }
        } catch (Exception e) {
            notificationService.notifyFailure("An error occurred during processing: " + e.getMessage());
        }
    }
}