package dd2480.group17.ciserver.service;

import dd2480.group17.ciserver.infrastructure.dto.WebhookDTO;
import dd2480.group17.ciserver.utils.Logger;

/**
 * The {@code WebhookService} class is responsible for processing incoming
 * webhook events.
 * <p>
 * It is responsible for event handling to specific methods based on the event
 * type.
 * Currently, the class
 * handles push events by cloning the repository, compiling the code, executing
 * tests, and sending
 * notifications based on the results via the NotificationService.
 * </p>
 */
public class WebhookService {

    private static final GitService gitService = new GitService();
    private static final CompileService compileService = new CompileService();
    private static final TestService testService = new TestService();
    private static final NotificationService notificationService = new NotificationService();
    private static final HistoryService historyService = new HistoryService();
    private static final Logger logger = new Logger();

    /**
     * Processes the incoming webhook event.
     *
     * @param webhookDTO the data transfer object containing the details of the
     *                   webhook event
     */
    public void processWebhookEvent(WebhookDTO webhookDTO) {
        // Error handling here or check if it is pushevent
        String commitId = webhookDTO.headCommit().id();
        String jsonData = webhookDTO.toString();
        String logMessage = "Received webhook event from: " + commitId + "\n";
        logger.logWebhookEvent(commitId, jsonData, logMessage);

        handlePushEvent(webhookDTO);
    }

    /**
     * Handles push events triggered by the webhook.
     * <p>
     * This method performs the following steps:
     * <ol>
     * <li>Extracts the repository URL, branch, and commit hash from the webhook
     * data.</li>
     * <li>Constructs a build path using the commit hash.</li>
     * <li>Attempts to clone the repository using {@link GitService}.</li>
     * <li>If the clone is successful, compiles the code and executes tests via
     * {@link CompileService} and {@link TestService}.</li>
     * <li>Sends a success notification through {@link NotificationService} if both
     * compilation
     * and testing succeed; otherwise, sends a failure notification.</li>
     * <li>If cloning fails or an exception occurs during processing, a failure
     * notification is sent.</li>
     * </ol>
     * </p>
     *
     * @param webhookDTO the data transfer object containing the details of the push
     *                   event
     */
    private void handlePushEvent(WebhookDTO webhookDTO) {
        String repoUrl = webhookDTO.repository().htmlUrl();
        String branch = webhookDTO.branch();
        String commitHash = webhookDTO.after();

        String path = "./builds/" + commitHash;

        try {
            boolean cloneSuccess = gitService.fetchRepository(repoUrl, branch, path);
            if (cloneSuccess) {
                boolean compileSuccess = compileService.compileCode(path, commitHash);
                boolean testSuccess = testService.executeTests(path, commitHash);

                // Notify based on the result of compile and test
                if (compileSuccess && testSuccess) {
                    System.out.println("test and compile: SUCCESS");
                    notificationService.notifySuccess(commitHash);
                } else {
                    System.out.println("test and compile: FAIL");
                    notificationService.notifyFailure(commitHash);
                }

            } else {
                System.out.println("test and compile: something went wrong");
                // Notify failure to clone repository
                // notificationService.notifyFailure("Failed to clone repository.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during processing: " + e.getMessage());
            notificationService.notifyFailure(commitHash);
        }
    }

}