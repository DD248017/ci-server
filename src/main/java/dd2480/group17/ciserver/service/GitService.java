package dd2480.group17.ciserver.service;

import dd2480.group17.ciserver.infrastructure.GitHandler;

public class GitService {

    private static final GitHandler gitHandler = new GitHandler();

    // TODO call githubhandler to get the repository
    public boolean fetchRepository(String repoUrl, String branch, String targetDir) {

        boolean successful = gitHandler.cloneRepository(repoUrl, branch, targetDir);
        // gitHandler.pullLatestChanges()
        // gitHandler.checkoutCommit()
        return successful;
    }

}
