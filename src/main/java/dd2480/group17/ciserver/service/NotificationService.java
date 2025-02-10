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
    public void notifySuccess(String commitHash) {
        statusSender.sendStatus(commitHash, true);
    }

    /**
     * Notifies GitHub that the build for the given commit has failed.
     *
     * @param commitHash The SHA hash of the commit to update the status for.
     */
    public void notifyFailure(String commitHash) {
        statusSender.sendStatus(commitHash, false);
    }
}
