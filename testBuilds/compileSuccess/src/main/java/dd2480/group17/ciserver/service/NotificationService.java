package dd2480.group17.ciserver.service;

import dd2480.group17.ciserver.infrastructure.StatusSender;

/**
 * The {@code NotificationService} class is responsible for notifying GitHub
 * about the success or failure of a build by updating the commit status.
 */
public class NotificationService {
    private static final StatusSender statusSender = new StatusSender();

    /**
     * Notifies GitHub that the build for the given commit was successful.
     *
     * @param commitHash The SHA hash of the commit to update the status for.
     */
    public int notifySuccess(String commitHash) {
        int statusCode = statusSender.sendStatus(commitHash, true);
        return statusCode;
    }

    /**
     * Notifies GitHub that the build for the given commit has failed.
     *
     * @param commitHash The SHA hash of the commit to update the status for.
     */
    public int notifyFailure(String commitHash) {
        int statusCode = statusSender.sendStatus(commitHash, false);
        return statusCode;
    }
}
