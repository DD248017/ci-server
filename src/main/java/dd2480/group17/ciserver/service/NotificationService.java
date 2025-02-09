package dd2480.group17.ciserver.service;

import dd2480.group17.ciserver.infrastructure.StatusSender;

public class NotificationService {
    private static final StatusSender statusSender = new StatusSender();

    // Maybe to this in one func

    // TODO call status sender
    public void notifySuccess(String commitHash) {
    }

    // TODO call status sender
    public void notifyFailure(String commitHash) {
    }

}
