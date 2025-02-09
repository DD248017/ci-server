package dd2480.group17.ciserver.service;

import dd2480.group17.ciserver.infrastructure.GitHandler;

public class GitService {

    private static final GitHandler gitHandler = new GitHandler();

    // TODO call githubhandler to get the repository
    public void fetchRepository(String repoUrl, String branch) {

        boolean successful = gitHandler.cloneRepository(repoUrl, branch, "./newRepo");
        // gitHandler.pullLatestChanges()
        // gitHandler.checkoutCommit()

    }

}
